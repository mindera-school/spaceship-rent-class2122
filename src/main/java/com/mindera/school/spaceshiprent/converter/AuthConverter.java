package com.mindera.school.spaceshiprent.converter;

import com.mindera.school.spaceshiprent.dto.auth.PrincipalDto;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public final class AuthConverter {

    public PrincipalDto convertToPrincipalDto(final UserEntity userEntity) {
        return PrincipalDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .userRole(userEntity.getUserType().getUserRole())
                .build();
    }
}
