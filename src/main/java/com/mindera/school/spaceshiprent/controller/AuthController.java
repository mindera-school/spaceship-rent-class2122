package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.auth.CredentialsDto;
import com.mindera.school.spaceshiprent.dto.auth.LoginResponseDto;
import com.mindera.school.spaceshiprent.service.auth.AuthService;
import com.mindera.school.spaceshiprent.util.LoggerHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.mindera.school.spaceshiprent.util.LoggerHelper.LOGIN;
import static com.mindera.school.spaceshiprent.util.LoggerHelper.REQUEST_TO;
import static com.mindera.school.spaceshiprent.util.LoggerHelper.newLogMessage;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    public static final String PATH_LOGIN = "/auth/login";

    private final AuthService authService;

    @PostMapping(PATH_LOGIN)
    public ResponseEntity<LoginResponseDto> login(@RequestBody final CredentialsDto credentials) {
        log.info(newLogMessage()
                .message(REQUEST_TO, LOGIN)
                .field("email", credentials.getEmail())
                .build());
        return ResponseEntity.ok(authService.login(credentials));
    }
}
