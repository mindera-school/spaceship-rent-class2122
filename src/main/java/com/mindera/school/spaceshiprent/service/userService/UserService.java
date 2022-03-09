package com.mindera.school.spaceshiprent.service.userService;

import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;

import java.util.List;

public interface UserService {
    UserDetailsDto createUser(CreateOrUpdateUserDto createOrUpdateUserDto);

    List<UserDetailsDto> getAllUsers();

    UserDetailsDto getUserById(Long id);

    UserDetailsDto updateUserById(Long id, CreateOrUpdateUserDto createOrUpdateUserDto);


}
