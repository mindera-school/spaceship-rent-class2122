package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.service.user.UserServiceImpl;
import com.mindera.school.spaceshiprent.util.LoggerHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/users")
    public ResponseEntity<UserDetailsDto> createUser(@RequestBody @Valid CreateOrUpdateUserDto userDto) {
        log.info(LoggerHelper.POST_REQUEST, LoggerHelper.USER, LocalDate.now());
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDetailsDto>> getUsers() {
        log.info(LoggerHelper.GET_ALL_REQUEST, LoggerHelper.USER, LocalDate.now());
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("@authorize.isUser(#id) || @authorize.hasRole('ADMIN')")
    public ResponseEntity<UserDetailsDto> getUserById(@PathVariable Long id) {
        log.info(LoggerHelper.GET_REQUEST, LoggerHelper.USER, LocalDate.now());
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDetailsDto> updateUser(@PathVariable Long id, @RequestBody @Valid CreateOrUpdateUserDto createOrUpdateUserDto) {
        log.info(LoggerHelper.PUT_REQUEST, LoggerHelper.USER, LocalDate.now());
        return ResponseEntity.ok(userService.updateUserById(id, createOrUpdateUserDto));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        log.info(LoggerHelper.DELETE_REQUEST, LoggerHelper.USER, LocalDate.now());
        userService.deleteUserById(id);
        return ResponseEntity.ok("User with id " + id + " was removed.");
    }
}
