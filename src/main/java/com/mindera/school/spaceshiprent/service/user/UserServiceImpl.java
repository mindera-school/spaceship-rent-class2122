package com.mindera.school.spaceshiprent.service.user;

import com.mindera.school.spaceshiprent.converter.UserConverter;
import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.exception.ErrorMessageConstants;
import com.mindera.school.spaceshiprent.exception.UserNotFoundException;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserConverter converter;
    private final UserRepository userRepository;

    @Override
    public UserDetailsDto createUser(CreateOrUpdateUserDto createOrUpdateUserDto) {
        UserEntity userEntity = converter.convertToEntity(createOrUpdateUserDto);
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
        Optional<UserEntity> userEntity = userRepository.findById(id);

        return userEntity
                .map(converter::convertToUserDetailsDto)
                .orElseThrow(() -> new UserNotFoundException(String.format(ErrorMessageConstants.USER_NOT_FOUND, id)));
    }

    @Override
    public UserDetailsDto updateUserById(Long id, CreateOrUpdateUserDto createOrUpdateUserDto) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if (userEntityOptional.isPresent()) {
            UserEntity user = converter.convertToEntity(createOrUpdateUserDto);
            user.setId(id);
            return converter.convertToUserDetailsDto(userRepository.save(user));

        }
        return null;
    }
}
