package com.mindera.school.spaceshiprent.dto.auth;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CredentialsDto {
    private String email;
    private String password;

    @Override
    public String toString() {
        return "CredentialsDto{" +
                "email='" + email + '\'' +
                '}';
    }
}
