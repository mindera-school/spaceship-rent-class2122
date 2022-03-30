package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.service.userService.UserServiceImpl;
import com.mindera.school.spaceshiprent.util.LoggerMessages;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserServiceImpl userService;

    @PostMapping()
    public ResponseEntity<UserDetailsDto> createUser(@RequestBody @Valid CreateOrUpdateUserDto userDto) {
        LOGGER.info(LoggerMessages.POST_REQUEST, LoggerMessages.USER, LocalDate.now());
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @GetMapping()
    public ResponseEntity<List<UserDetailsDto>> getUsers() {
        LOGGER.info(LoggerMessages.GET_ALL_REQUEST, LoggerMessages.USER, LocalDate.now());
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDto> getUserById(@PathVariable Long id) {
        LOGGER.info(LoggerMessages.GET_REQUEST, LoggerMessages.USER, LocalDate.now());
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetailsDto> updateUser(@PathVariable Long id, @RequestBody @Valid CreateOrUpdateUserDto createOrUpdateUserDto) {
        LOGGER.info(LoggerMessages.PUT_REQUEST, LoggerMessages.USER, LocalDate.now());
        return ResponseEntity.ok(userService.updateUserById(id, createOrUpdateUserDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        LOGGER.info(LoggerMessages.DELETE_REQUEST, LoggerMessages.USER, LocalDate.now());
        userService.deleteUserById(id);
        return ResponseEntity.ok("User with id " + id + " was removed.");
    }
}
