package com.mindera.school.spaceshiprent.converter;

import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceShipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceShipEntity;

public class SpaceShipConverter {

    public static SpaceShipEntity fromCreateOrUpdateDto(CreateOrUpdateSpaceShipDto dto){
        return SpaceShipEntity.builder()
                .name(dto.getName())
                .brand(dto.getBrand())
                .model(dto.getModel())
                .registerNumber(dto.getRegisterNumber())
                .priceDay(dto.getPriceDay())
                .build();
    }

    public static SpaceShipDetailsDto toSpaceShipDetailsDto(SpaceShipEntity spaceShipEntity){
        return SpaceShipDetailsDto.builder()
                .id(spaceShipEntity.getId())
                .name(spaceShipEntity.getName())
                .brand(spaceShipEntity.getBrand())
                .model(spaceShipEntity.getModel())
                .registerNumber(spaceShipEntity.getRegisterNumber())
                .priceDay(spaceShipEntity.getPriceDay())
                .build();
    }
}
