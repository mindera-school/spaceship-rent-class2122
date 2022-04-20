package com.mindera.school.spaceshiprent.dto.auth;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserLoginDto {

    @Email
    private String email;

    @NotBlank
    private String password;
}
