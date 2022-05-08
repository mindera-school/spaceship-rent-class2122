package com.mindera.school.spaceshiprent.service.spaceship;

import com.mindera.school.spaceshiprent.converter.SpaceshipConverter;
import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceshipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceshipDetailsDto;
import com.mindera.school.spaceshiprent.exception.ErrorMessageConstants;
import com.mindera.school.spaceshiprent.exception.exceptions.SpaceshipNotFoundException;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpaceShipServiceImpl implements SpaceShipService {

    private final SpaceshipConverter converter;
    private final SpaceshipRepository spaceShipRepository;

    @Override
    public SpaceshipDetailsDto createSpaceShip(CreateOrUpdateSpaceshipDto createOrUpdateSpaceShipDto) {

        SpaceshipEntity spaceshipEntity = converter.convertToEntity(createOrUpdateSpaceShipDto);

        return converter.convertToSpaceShipDetailsDto(
                spaceShipRepository.save(spaceshipEntity));
    }

    @Override
    public List<SpaceshipDetailsDto> getAllSpaceShips() {
        return spaceShipRepository.findAll().stream()
                .map(converter::convertToSpaceShipDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    public SpaceshipDetailsDto getSpaceShipById(Long id) {
        SpaceshipEntity spaceshipEntity = spaceShipRepository.findById(id)
                .orElseThrow(() -> new SpaceshipNotFoundException(String.format(ErrorMessageConstants.SPACESHIP_NOT_FOUND, id)));

        return converter.convertToSpaceShipDetailsDto(spaceshipEntity);
    }

    @Override
    public SpaceshipDetailsDto updateSpaceShipById(
            Long id,
            CreateOrUpdateSpaceshipDto createOrUpdateSpaceShipDto
    ) {
        SpaceshipEntity spaceshipFromDb = spaceShipRepository.findById(id)
                .orElseThrow(() -> new SpaceshipNotFoundException(String.format(ErrorMessageConstants.SPACESHIP_NOT_FOUND, id)));

        SpaceshipEntity spaceShip = converter.convertToEntity(createOrUpdateSpaceShipDto);
        spaceShip.setId(id);

        return converter.convertToSpaceShipDetailsDto(
                spaceShipRepository.save(spaceShip));
    }

}
