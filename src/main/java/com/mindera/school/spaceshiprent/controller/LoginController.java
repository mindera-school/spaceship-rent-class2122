package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.login.UserLoginDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.service.login.LoginServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {


    private final LoginServiceImpl loginServiceImpl;

    @PostMapping("/")
    public ResponseEntity<UserDetailsDto> loginUser(@RequestBody UserLoginDto dto) {
        return ResponseEntity.ok(loginServiceImpl.checkCredentials(dto));
    }


}
