package com.mindera.school.spaceshiprent.controller;


import com.mindera.school.spaceshiprent.dto.auth.LoginDto;
import com.mindera.school.spaceshiprent.dto.auth.SendTokenDto;
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
    public ResponseEntity<SendTokenDto> login (@RequestBody LoginDto login){
        if(login.getEmail().equals("") || login.getPassword().equals("")){
            throw new IllegalArgumentException();
        }
        return ResponseEntity.ok(authService.loginToken(login));
    }
}
