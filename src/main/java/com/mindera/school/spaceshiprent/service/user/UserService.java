package com.mindera.school.spaceshiprent.service.user;

import com.mindera.school.spaceshiprent.dto.auth.LoginDto;
import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.auth.CredentialsDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;

import java.util.List;

public interface UserService {
    UserDetailsDto createUser(CreateOrUpdateUserDto createOrUpdateUserDto);

    List<UserDetailsDto> getAllUsers();

    UserDetailsDto getUserById(Long id);

    UserDetailsDto updateUserById(Long id, CreateOrUpdateUserDto createOrUpdateUserDto);
}
