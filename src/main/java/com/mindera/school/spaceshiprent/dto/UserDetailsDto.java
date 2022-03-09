package com.mindera.school.spaceshiprent.dto;

import com.mindera.school.spaceshiprent.enumerator.UserType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailsDto {
    private Long id;
    private String name;
    private int age;
    private String licenseNumber;
    private String ssn;
    private String planet;
    private String email;
    private UserType userType;
}
