package com.mindera.school.spaceshiprent.controller;


import com.mindera.school.spaceshiprent.dto.user.LoginDto.LoginDto;
import com.mindera.school.spaceshiprent.dto.user.LoginDto.ValidLoginDto;
import com.mindera.school.spaceshiprent.service.auth.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/login")

    public ResponseEntity<ValidLoginDto> login(@RequestBody LoginDto loginDto) throws AccountNotFoundException {
        log.info("Login with email {}",loginDto.getEmail());

        ValidLoginDto validLoginDto = authServiceImpl.validLoginDto(loginDto);

        ResponseCookie cookie = ResponseCookie.from("SpaceShip",validLoginDto.getToken())
                .httpOnly(true)
                .secure(false)
                .maxAge(-1)
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).body(validLoginDto);
    }
}
