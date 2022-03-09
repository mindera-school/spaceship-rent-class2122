package com.mindera.school.spaceshiprent.service;

import com.mindera.school.spaceshiprent.converter.UserConverter;
import com.mindera.school.spaceshiprent.dto.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.UserDetailsDto;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public UserDetailsDto createUser(CreateOrUpdateUserDto createOrUpdateUserDto) {
        UserEntity userEntity = UserConverter.fromCreateOrUpdateDto(createOrUpdateUserDto);
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

        return userEntity.map(UserConverter::toUserDetailsDto).orElse(null);
    }

    @Override
    public UserDetailsDto updateUserById(Long id, CreateOrUpdateUserDto createOrUpdateUserDto) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if (userEntityOptional.isPresent()) {
            UserEntity user = UserConverter.fromCreateOrUpdateDto(createOrUpdateUserDto);
            user.setId(id);
            return UserConverter.toUserDetailsDto(userRepository.save(user));

        }
        return null;
    }
}
