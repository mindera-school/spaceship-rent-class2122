package com.mindera.school.spaceshiprent.dto.auth;

import com.mindera.school.spaceshiprent.enumerator.UserType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDetailsDto {

    private Long id;
    private String name;
    private String planet;
    private String email;
    private UserType userType;
    private String token;
}
