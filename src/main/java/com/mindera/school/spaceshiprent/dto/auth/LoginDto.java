package com.mindera.school.spaceshiprent.dto.auth;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {

    @Email
    private String email;

    @NotBlank
    private String password;

}
