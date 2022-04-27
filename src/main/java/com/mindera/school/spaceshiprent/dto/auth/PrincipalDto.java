package com.mindera.school.spaceshiprent.dto.auth;

import com.mindera.school.spaceshiprent.enumerator.UserRole;
import com.mindera.school.spaceshiprent.enumerator.UserType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PrincipalDto {

    private Long id;
    private String name;
    private String email;
    private UserRole userRole;
}
