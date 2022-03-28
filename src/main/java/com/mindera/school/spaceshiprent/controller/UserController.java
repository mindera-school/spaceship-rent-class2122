package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.components.messages.MessageSender;
import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.aspectj.bridge.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final MessageSender MESSAGESENDER;

    @PostMapping("/users")
    public ResponseEntity<UserDetailsDto> createUser(@RequestBody CreateOrUpdateUserDto createOrUpdateUserDto) {
        log.info("created user {}",createOrUpdateUserDto);
        UserDetailsDto userDetailsDtoResponseEntity = userService.createUser(createOrUpdateUserDto);
        MESSAGESENDER.sendUserCreated(userDetailsDtoResponseEntity.toString());
        return ResponseEntity.ok(userDetailsDtoResponseEntity);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDetailsDto>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDetailsDto> getUserById(@PathVariable Long id) {
        log.info("get user {}",id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDetailsDto> updateUser(@PathVariable Long id, @RequestBody CreateOrUpdateUserDto createOrUpdateUserDto) {
        log.info("created user {}{}",createOrUpdateUserDto,id);
        return ResponseEntity.ok(userService.updateUserById(id,createOrUpdateUserDto));
    }
}
