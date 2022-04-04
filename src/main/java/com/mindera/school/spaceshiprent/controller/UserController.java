package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.Rabbit.QueueSend;
import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.service.userService.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    private final QueueSend queueSend;

    @PostMapping()
    public ResponseEntity<UserDetailsDto> createUser(@RequestBody CreateOrUpdateUserDto createOrUpdateUserDto) {
        log.info("Request to create user");
        queueSend.send(createOrUpdateUserDto.toString());
        return ResponseEntity.ok(userService.createUser(createOrUpdateUserDto));
    }

    @GetMapping()
    public ResponseEntity<List<UserDetailsDto>> getUsers() {
        log.info("Request to get all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDto> getUserById(@PathVariable Long id) {
        log.info("Request to get user by id {}", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDetailsDto> updateUser(@PathVariable Long id,
                                                     @RequestBody CreateOrUpdateUserDto createOrUpdateUserDto){
        log.info("Request to get user by id {}", id);
        return ResponseEntity.ok(userService.updateUserById(id,createOrUpdateUserDto));
    }
}
