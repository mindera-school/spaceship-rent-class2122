package com.mindera.school.spaceshiprent.service.spaceship;

import com.mindera.school.spaceshiprent.converter.SpaceshipConverter;
import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceshipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
<<<<<<< HEAD:src/main/java/com/mindera/school/spaceshiprent/service/spaceShipService/SpaceShipServiceImpl.java
import com.mindera.school.spaceshiprent.exception.ErrorMessages;
import com.mindera.school.spaceshiprent.exception.SpaceShipNotFoundException;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceShipEntity;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceShipRepository;
=======
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceshipRepository;
>>>>>>> master:src/main/java/com/mindera/school/spaceshiprent/service/spaceship/SpaceShipServiceImpl.java
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
<<<<<<< HEAD:src/main/java/com/mindera/school/spaceshiprent/service/spaceShipService/SpaceShipServiceImpl.java
        Optional<SpaceShipEntity> spaceShipEntity = spaceShipRepository.findById(id);
        return spaceShipEntity.map(SpaceShipConverter::toSpaceShipDetailsDto)
                .orElseThrow(() -> new SpaceShipNotFoundException(String.format(ErrorMessages.SPACESHIP_NOT_FOUND,id)));
=======

        Optional<SpaceshipEntity> spaceShipEntity = spaceShipRepository.findById(id);

        return spaceShipEntity
                .map(converter::convertToSpaceShipDetailsDto)
                .orElse(null);
>>>>>>> master:src/main/java/com/mindera/school/spaceshiprent/service/spaceship/SpaceShipServiceImpl.java
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
