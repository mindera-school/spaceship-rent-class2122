package com.mindera.school.spaceshiprent.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {

    PrincipalDto principal;
    String token;
}
