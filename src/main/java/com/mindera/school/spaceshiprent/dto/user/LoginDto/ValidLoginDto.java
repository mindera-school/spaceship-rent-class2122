package com.mindera.school.spaceshiprent.dto.user.LoginDto;

import com.mindera.school.spaceshiprent.enumerator.UserType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidLoginDto {

    private Long id;
    private String name;
    private String email;
    private UserType userType;
    private String token;

}