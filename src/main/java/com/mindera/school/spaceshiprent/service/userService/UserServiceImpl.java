package com.mindera.school.spaceshiprent.service.userService;

import com.mindera.school.spaceshiprent.converter.UserConverter;
import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.user.CredentialsDto;
import com.mindera.school.spaceshiprent.dto.user.LoginDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.exception.AccountNotFound;
import com.mindera.school.spaceshiprent.exception.ErrorMessages;
import com.mindera.school.spaceshiprent.exception.UserNotFoundException;
import com.mindera.school.spaceshiprent.messaging.SpaceshipRentQueueSender;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import com.mindera.school.spaceshiprent.security.JWTManager;
import com.mindera.school.spaceshiprent.service.userService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JWTManager jwtManager;
    UserConverter userConverter;
   // private final SpaceshipRentQueueSender sender;


    @Override
    public UserDetailsDto createUser(CreateOrUpdateUserDto createOrUpdateUserDto) {
        UserEntity userEntity = UserConverter.fromCreateOrUpdateDto(createOrUpdateUserDto);
        String encodedPassword = this.passwordEncoder.encode(createOrUpdateUserDto.getPassword());
        userEntity.setPassword(encodedPassword);
      //  sender.send(userEntity.getEmail());
        return UserConverter.toUserDetailsDto(userRepository.save(userEntity));
    }

    @Override
    public List<UserDetailsDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserConverter::toUserDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailsDto getUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);

        return userEntity
                .map(UserConverter::toUserDetailsDto)
                .orElseThrow(() -> new UserNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND, id)));
    }


    @Override
    public UserDetailsDto updateUserById(Long id, CreateOrUpdateUserDto createOrUpdateUserDto) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if (userEntityOptional.isPresent()) {
            UserEntity user = UserConverter.fromCreateOrUpdateDto(createOrUpdateUserDto);
            user.setId(id);
            return UserConverter.toUserDetailsDto(userRepository.save(user));

        }

        return userEntityOptional
                .map(UserConverter::toUserDetailsDto)
                .orElseThrow(() -> new UserNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND, id)));
    }


    @Override
    public LoginDto login(CredentialsDto credentials) {
      UserEntity userEntity = userRepository.findUserByEmail(credentials.getEmail())
              .orElseThrow(() -> {
                  log.warn("Email entered does not exist: {}", credentials.getEmail());
                  return new AccountNotFound("Email or password are wrong");
      });

        if (passwordEncoder.matches(credentials.getPassword(), userEntity.getPassword())) {
            String token = jwtManager.generateToken(userEntity);

            log.info("login token created: {}", token);

            LoginDto userWithToken = userConverter.toLoginDto(userEntity);
            userWithToken.setToken(token);
            return userWithToken;
        }

        throw new AccountNotFound("Email or password are wrong");
    }

}