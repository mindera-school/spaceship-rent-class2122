package com.mindera.school.spaceshiprent.dto.user;

import com.mindera.school.spaceshiprent.enumerator.UserType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDto {

    private String token;
    private Long id;
    private String name;
    private String email;
    private Long ssn;
    private UserType userType;
    private String licenseNumber;
}