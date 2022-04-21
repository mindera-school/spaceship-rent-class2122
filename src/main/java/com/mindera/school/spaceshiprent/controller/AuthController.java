package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.auth.UserLoginDto;
import com.mindera.school.spaceshiprent.dto.auth.ValidLoginDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.service.auth.LoginServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class AuthController {


    private final LoginServiceImpl loginServiceImpl;

    @PostMapping("/")
    public ResponseEntity<ValidLoginDto> loginUser(@RequestBody UserLoginDto dto) {
        return ResponseEntity.ok(loginServiceImpl.checkCredentials(dto));
    }


}
