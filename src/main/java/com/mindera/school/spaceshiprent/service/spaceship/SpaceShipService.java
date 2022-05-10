package com.mindera.school.spaceshiprent.service.spaceship;

import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceshipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceshipDetailsDto;

import java.util.List;

public interface SpaceShipService {

    SpaceshipDetailsDto createSpaceShip(CreateOrUpdateSpaceshipDto createOrUpdateSpaceShipDto);

    List<SpaceshipDetailsDto> getAllSpaceShips();

    SpaceshipDetailsDto getSpaceShipById(Long id);

    SpaceshipDetailsDto updateSpaceShipById(Long id, CreateOrUpdateSpaceshipDto createOrUpdateSpaceShipDto);
}
