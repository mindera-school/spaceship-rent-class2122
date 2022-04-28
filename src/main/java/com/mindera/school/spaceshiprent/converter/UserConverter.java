package com.mindera.school.spaceshiprent.converter;

import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.user.LoginDto.ValidLoginDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final JWTUtils jwtUtils;

    public UserEntity convertToEntity(CreateOrUpdateUserDto dto) {
        return UserEntity.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .age(dto.getAge())
                .licenseNumber(dto.getLicenseNumber())
                .ssn(dto.getSsn())
                .planet(dto.getPlanet())
                .userType(dto.getUserType())
                .build();
    }

    public UserDetailsDto convertToUserDetailsDto(UserEntity entity) {
        return UserDetailsDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .age(entity.getAge())
                .licenseNumber(entity.getLicenseNumber())
                .ssn(entity.getSsn())
                .planet(entity.getPlanet())
                .userType(entity.getUserType())
                .email(entity.getEmail())
                .build();
    }

    public ValidLoginDto convertToValidLoginDto(UserEntity entity){
        return ValidLoginDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .name(entity.getName())
                .userType(entity.getUserType())
                .token(jwtUtils.generateToken(entity))
                .build();
    }
}
