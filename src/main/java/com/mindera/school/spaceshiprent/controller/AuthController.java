package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.auth.LoginDTO;
import com.mindera.school.spaceshiprent.dto.auth.SendTokenDTO;
import com.mindera.school.spaceshiprent.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<SendTokenDTO> login(@RequestBody LoginDTO login) {
        if(login.getEmail().equals("") || login.getPassword().equals("")) {
            throw new IllegalArgumentException();
        }
    return ResponseEntity.ok(authService.effectuateLogin(login));
    }

}


