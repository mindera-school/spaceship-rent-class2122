package com.mindera.school.spaceshiprent.service.user;

import com.mindera.school.spaceshiprent.components.EmailSender;
import com.mindera.school.spaceshiprent.converter.UserConverter;
import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.exception.exceptions.UserAlreadyExists;
import com.mindera.school.spaceshiprent.exception.exceptions.UserNotFoundException;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.mindera.school.spaceshiprent.exception.ErrorMessageConstants.USER_ALREADY_EXISTS;
import static com.mindera.school.spaceshiprent.exception.ErrorMessageConstants.USER_NOT_FOUND;
import static com.mindera.school.spaceshiprent.util.LoggerMessages.CREATED;
import static com.mindera.school.spaceshiprent.util.LoggerMessages.USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserConverter converter;
    private final UserRepository userRepository;
    private final EmailSender emailSender;

    @Override
    public UserDetailsDto createUser(CreateOrUpdateUserDto createOrUpdateUserDto) {
        userRepository.findByEmail(createOrUpdateUserDto.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExists(USER_ALREADY_EXISTS);
                });

        UserEntity userEntity = converter.convertToEntity(createOrUpdateUserDto);

        String emailingInfo = userEntity.getEmail() + " " + userEntity.getName();
        emailSender.send(emailingInfo);

        log.info(CREATED, USER);
        return converter.convertToUserDetailsDto(userRepository.save(userEntity));
    }

    @Override
    public List<UserDetailsDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(converter::convertToUserDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailsDto getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND, id)));

        return converter.convertToUserDetailsDto(userEntity);
    }

    @Override
    public UserDetailsDto updateUserById(Long id, CreateOrUpdateUserDto createOrUpdateUserDto) {
        UserEntity userFromDb = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND, id)));

        UserEntity user = converter.convertToEntity(createOrUpdateUserDto);
        user.setId(id);

        return converter.convertToUserDetailsDto(userRepository.save(user));
    }

}
