package com.mindera.school.spaceshiprent.service.spaceship;

import com.mindera.school.spaceshiprent.converter.SpaceshipConverter;
import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceshipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpaceShipServiceImpl  implements SpaceShipService{

    private final SpaceshipConverter converter;
    private final SpaceshipRepository spaceShipRepository;

    @Override
    public SpaceShipDetailsDto createSpaceShip(CreateOrUpdateSpaceshipDto createOrUpdateSpaceShipDto) {

        SpaceshipEntity spaceshipEntity = converter.convertToEntity(createOrUpdateSpaceShipDto);

        return converter.convertToSpaceShipDetailsDto(
                spaceShipRepository.save(spaceshipEntity));
    }

    @Override
    public List<SpaceShipDetailsDto> getAllSpaceShips() {

        return spaceShipRepository.findAll().stream()
                .map(converter::convertToSpaceShipDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    public SpaceShipDetailsDto getSpaceShipById(Long id) {

        Optional<SpaceshipEntity> spaceShipEntity = spaceShipRepository.findById(id);

        return spaceShipEntity
                .map(converter::convertToSpaceShipDetailsDto)
                .orElse(null);
    }

    @Override
    public SpaceShipDetailsDto updateSpaceShipById(Long id, CreateOrUpdateSpaceshipDto createOrUpdateSpaceShipDto) {
        Optional<SpaceshipEntity> spaceShipEntityOptional = spaceShipRepository.findById(id);

        if(spaceShipEntityOptional.isPresent()){
            SpaceshipEntity spaceShip = converter.convertToEntity(createOrUpdateSpaceShipDto);
            spaceShip.setId(id);
            return converter.convertToSpaceShipDetailsDto(
                    spaceShipRepository.save(spaceShip));
        }

        return null;
    }
}
