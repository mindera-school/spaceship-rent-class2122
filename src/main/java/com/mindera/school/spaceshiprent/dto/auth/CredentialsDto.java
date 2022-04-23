package com.mindera.school.spaceshiprent.dto.auth;

import lombok.Data;

@Data
public class CredentialsDto {
    private String email;
    private String password;

    @Override
    public String toString() {
        return "CredentialsDto{" +
                "email='" + email + '\'' +
                ", password='***'" +
                '}';
    }
}
