package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.service.user.UserServiceImpl;
import com.mindera.school.spaceshiprent.util.LoggerMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    public static final String PATH_CREATE_USER = "spaceship-rent/api/users";
    public static final String PATH_GET_ALL_USERS = "spaceship-rent/api/users";
    public static final String PATH_GET_USER_BY_ID = "spaceship-rent/api/users/{id}";
    public static final String PATH_UPDATE_USER_BY_ID = "spaceship-rent/api/users/{id}";

    @PostMapping(PATH_CREATE_USER)
    public ResponseEntity<UserDetailsDto> createUser(@RequestBody @Valid CreateOrUpdateUserDto userDto) {
        log.info(LoggerMessages.POST_REQUEST, LoggerMessages.USER, LocalDate.now());
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @GetMapping(PATH_GET_ALL_USERS)
    public ResponseEntity<List<UserDetailsDto>> getUsers() {
        log.info(LoggerMessages.GET_ALL_REQUEST, LoggerMessages.USER, LocalDate.now());
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(PATH_GET_USER_BY_ID)
    public ResponseEntity<UserDetailsDto> getUserById(@PathVariable Long id) {
        log.info(LoggerMessages.GET_REQUEST, LoggerMessages.USER, LocalDate.now());
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping(PATH_UPDATE_USER_BY_ID)
    public ResponseEntity<UserDetailsDto> updateUser(@PathVariable Long id, @RequestBody @Valid CreateOrUpdateUserDto createOrUpdateUserDto) {
        log.info(LoggerMessages.PUT_REQUEST, LoggerMessages.USER, LocalDate.now());
        return ResponseEntity.ok(userService.updateUserById(id, createOrUpdateUserDto));
    }
}