package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.user.CredentialsDto;
import com.mindera.school.spaceshiprent.dto.user.LoginDto;
import com.mindera.school.spaceshiprent.service.userService.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImpl userService;


    public ResponseEntity<LoginDto> login (@RequestBody CredentialsDto credentials) {
        log.info("user logged in");
        return ResponseEntity.ok(userService.login(credentials));
    }
}
