package com.mindera.school.spaceshiprent.dto.user;

import com.mindera.school.spaceshiprent.enumerator.UserType;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder

public class UserDetailsDto {
    private Long id;
    private String name;
    private int age;
    private String licenseNumber;
    private Long ssn;
    private String planet;
    private String email;
    private UserType userType;

    public Map<String, Object> toMap() {
        return Map.of(
                "id", id,
                "name", name,
                "age", age,
                "licenseNumber", licenseNumber,
                "ssn", ssn,
                "planet", planet,
                "email", email,
                "userType", userType
        );
    }
}
