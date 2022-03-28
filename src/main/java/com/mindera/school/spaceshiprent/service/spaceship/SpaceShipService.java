package com.mindera.school.spaceshiprent.service.spaceship;

import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceshipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;

import java.util.List;

public interface SpaceShipService {

    SpaceShipDetailsDto createSpaceShip(CreateOrUpdateSpaceshipDto createOrUpdateSpaceShipDto);

    List<SpaceShipDetailsDto> getAllSpaceShips();

    SpaceShipDetailsDto getSpaceShipById(Long id);

    SpaceShipDetailsDto updateSpaceShipById(Long id, CreateOrUpdateSpaceshipDto createOrUpdateSpaceShipDto);
}
