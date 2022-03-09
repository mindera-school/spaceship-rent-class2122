package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.service.userService.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")

public class UserController {

    private final UserServiceImpl userService;

    @PostMapping()
    public ResponseEntity<UserDetailsDto> createUser(@RequestBody CreateOrUpdateUserDto createOrUpdateUserDto) {
        return ResponseEntity.ok(userService.createUser(createOrUpdateUserDto));
    }

    @GetMapping()
    public ResponseEntity<List<UserDetailsDto>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetailsDto> updateUser(@PathVariable Long id, @RequestBody CreateOrUpdateUserDto createOrUpdateUserDto) {
        return ResponseEntity.ok(userService.updateUserById(id,createOrUpdateUserDto));
    }
}
