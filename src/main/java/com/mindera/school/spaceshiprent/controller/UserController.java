package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.service.user.UserServiceImpl;
import com.mindera.school.spaceshiprent.util.LoggerMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/users")
    public ResponseEntity<UserDetailsDto> createUser(@RequestBody @Valid CreateOrUpdateUserDto userDto) {
        log.info(LoggerMessages.POST_REQUEST, LoggerMessages.USER);
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDetailsDto>> getUsers() {
        log.info(LoggerMessages.GET_ALL_REQUEST, LoggerMessages.USER);
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDetailsDto> getUserById(@PathVariable Long id) {
        log.info(LoggerMessages.GET_REQUEST, LoggerMessages.USER);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDetailsDto> updateUser(
            @PathVariable Long id,
            @RequestBody @Valid CreateOrUpdateUserDto createOrUpdateUserDto) {
        log.info(LoggerMessages.PUT_REQUEST, LoggerMessages.USER);
        return ResponseEntity.ok(userService.updateUserById(id, createOrUpdateUserDto));
    }

}
