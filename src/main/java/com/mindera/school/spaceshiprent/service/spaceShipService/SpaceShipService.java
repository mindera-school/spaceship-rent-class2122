package com.mindera.school.spaceshiprent.service.spaceShipService;

import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceShipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;

import java.util.List;

public interface SpaceShipService {

    SpaceShipDetailsDto createSpaceShip(CreateOrUpdateSpaceShipDto createOrUpdateSpaceShipDto);

    List<SpaceShipDetailsDto> getAllSpaceShips();

    SpaceShipDetailsDto getSpaceShipById(Long id);

    SpaceShipDetailsDto updateSpaceShipById(Long id, CreateOrUpdateSpaceShipDto createOrUpdateSpaceShipDto);
}
