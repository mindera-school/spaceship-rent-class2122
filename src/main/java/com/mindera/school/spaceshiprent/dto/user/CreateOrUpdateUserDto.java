package com.mindera.school.spaceshiprent.dto.user;

import com.mindera.school.spaceshiprent.enumerator.UserType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@Builder
public class CreateOrUpdateUserDto {

    @NotNull
    private String name;

    @Min(value = 21, message = "Age should not be less than 21")
    @Max(value = 150, message = "Age should not be greater than 150")
    private int age;

    @NotNull
    private String licenseNumber;

    @NotNull
    private Long ssn;

    @NotBlank
    private String planet;

    @Email(message = "Insert a valid email.")
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#@$!%*?&])[A-Za-z\\d@$#!%*?&]{8,}$",
            message = "password doesn't match the requirements")
    private String password;

    @NotNull
    private UserType userType;
}
