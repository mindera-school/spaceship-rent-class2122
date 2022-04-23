package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.auth.CredentialsDto;
import com.mindera.school.spaceshiprent.dto.auth.LoginResponseDto;
import com.mindera.school.spaceshiprent.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody final CredentialsDto credentials) {
        log.info("User trying to login with credentials: {}", credentials);
        return ResponseEntity.ok(authService.login(credentials));
    }
}
