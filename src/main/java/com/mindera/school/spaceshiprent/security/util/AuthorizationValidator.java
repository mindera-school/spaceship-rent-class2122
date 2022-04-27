package com.mindera.school.spaceshiprent.security.util;

import com.mindera.school.spaceshiprent.dto.auth.PrincipalDto;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthorizationValidator {

    public boolean hasRole(String userType) {
        return userType.equals(getPrincipal().getUserRole().name());
    }

    public boolean isUser(Long userId) {
        return userId.equals(getPrincipal().getId());
    }

    private PrincipalDto getPrincipal() {
        return (PrincipalDto) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }

}
