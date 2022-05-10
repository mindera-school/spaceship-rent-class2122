package com.mindera.school.spaceshiprent.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
