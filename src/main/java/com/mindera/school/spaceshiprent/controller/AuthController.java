package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.auth.LoginDetailsDto;
import com.mindera.school.spaceshiprent.dto.auth.LoginDto;
import com.mindera.school.spaceshiprent.service.Auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
    public ResponseEntity<LoginDetailsDto> login (@RequestBody LoginDto loginDto) {
        log.info("Login requested with email {}", loginDto.getEmail());

        LoginDetailsDto loginDetailsDto = authService.login(loginDto);

        ResponseCookie jwtCookie = ResponseCookie
                .from("Rent-a-Spaceship", loginDetailsDto.getToken())
                .httpOnly(true)
                .secure(false)
                .maxAge(-1)
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(loginDetailsDto);
    }

}
