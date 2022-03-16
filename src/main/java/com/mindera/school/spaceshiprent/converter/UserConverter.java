package com.mindera.school.spaceshiprent.converter;

import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;

public class UserConverter {

    public static UserEntity fromCreateOrUpdateDto(CreateOrUpdateUserDto dto) {
        return UserEntity.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .age(dto.getAge())
                .licenseNumber(dto.getLicenseNumber())
                .ssn(dto.getSsn())
                .planet(dto.getPlanet())
                .password(dto.getPassword())
                .userType(dto.getUserType())
                .build();
    }

    public static UserDetailsDto toUserDetailsDto(UserEntity entity) {
        return UserDetailsDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .age(entity.getAge())
                .licenseNumber(entity.getLicenseNumber())
                .ssn(entity.getSsn())
                .planet(entity.getPlanet())
                .userType(entity.getUserType())
                .email(entity.getEmail())
                .build();
    }
}
