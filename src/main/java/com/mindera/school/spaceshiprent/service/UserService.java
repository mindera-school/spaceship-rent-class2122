package com.mindera.school.spaceshiprent.service;

import com.mindera.school.spaceshiprent.dto.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.UserDetailsDto;

import java.util.List;

public interface UserService {
    UserDetailsDto createUser(CreateOrUpdateUserDto createOrUpdateUserDto);

    List<UserDetailsDto> getAllUsers();

    UserDetailsDto getUserById(Long id);

    UserDetailsDto updateUserById(Long id, CreateOrUpdateUserDto createOrUpdateUserDto);


}
