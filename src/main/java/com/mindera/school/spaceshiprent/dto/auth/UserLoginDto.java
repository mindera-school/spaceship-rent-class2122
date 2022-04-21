package com.mindera.school.spaceshiprent.dto.auth;

import lombok.Data;

@Data
public class UserLoginDto {
    private String email;
    private String password;
}
