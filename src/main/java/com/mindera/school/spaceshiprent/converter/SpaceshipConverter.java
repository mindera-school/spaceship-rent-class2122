package com.mindera.school.spaceshiprent.converter;

import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceshipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceshipDetailsDto;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import org.springframework.stereotype.Component;

@Component
public class SpaceshipConverter {

    public SpaceshipEntity convertToEntity(CreateOrUpdateSpaceshipDto dto){
        return SpaceshipEntity.builder()
                .name(dto.getName())
                .brand(dto.getBrand())
                .model(dto.getModel())
                .registerNumber(dto.getRegisterNumber())
                .priceDay(dto.getPriceDay())
                .build();
    }

    public SpaceshipDetailsDto convertToSpaceShipDetailsDto(SpaceshipEntity entity){
        return SpaceshipDetailsDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .brand(entity.getBrand())
                .model(entity.getModel())
                .registerNumber(entity.getRegisterNumber())
                .priceDay(entity.getPriceDay())
                .build();
    }
}
