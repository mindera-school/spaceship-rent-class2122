package com.mindera.school.spaceshiprent.dto.user;

import com.mindera.school.spaceshiprent.enumerator.UserType;
import lombok.Data;

@Data
public class CreateOrUpdateUserDto {

    private String name;
    private int age;
    private String licenseNumber;
    private Long ssn;
    private String planet;
    private String email;
    private String password;
    private UserType userType;
}
