package com.mindera.school.spaceshiprent.service.spaceShipService;

import com.mindera.school.spaceshiprent.converter.SpaceShipConverter;
import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceShipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.exception.ErrorMessages;
import com.mindera.school.spaceshiprent.exception.SpaceShipNotFound;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceShipEntity;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceShipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpaceShipServiceImpl  implements SpaceShipService{

    private final SpaceShipRepository spaceShipRepository;

    @Override
    public SpaceShipDetailsDto createSpaceShip(CreateOrUpdateSpaceShipDto createOrUpdateSpaceShipDto) {
        SpaceShipEntity spaceshipEntity = SpaceShipConverter.fromCreateOrUpdateDto(createOrUpdateSpaceShipDto);
        return SpaceShipConverter.toSpaceShipDetailsDto(spaceShipRepository.save(spaceshipEntity));

    }

    @Override
    public List<SpaceShipDetailsDto> getAllSpaceShips() {
        List<SpaceShipDetailsDto> spaceShipList = spaceShipRepository.findAll().stream()
                .map(SpaceShipConverter::toSpaceShipDetailsDto)
                .collect(Collectors.toList());
        if(spaceShipList.size() > 0){
            return spaceShipList;
        }else{
            throw new SpaceShipNotFound(String.format(ErrorMessages.SPACESHIPS_NOT_FOUND));
        }
    }

    @Override
    public SpaceShipDetailsDto getSpaceShipById(Long id) {
        Optional<SpaceShipEntity> spaceShipEntity = spaceShipRepository.findById(id);
        return spaceShipEntity.map(SpaceShipConverter::toSpaceShipDetailsDto)
                .orElseThrow(() -> new SpaceShipNotFound(String.format(ErrorMessages.SPACESHIP_NOT_FOUND,id)));
    }

    @Override
    public SpaceShipDetailsDto updateSpaceShipById(Long id, CreateOrUpdateSpaceShipDto createOrUpdateSpaceShipDto) {
        Optional<SpaceShipEntity> spaceShipEntityOptional = spaceShipRepository.findById(id);

        if(spaceShipEntityOptional.isPresent()){
            SpaceShipEntity spaceShip = SpaceShipConverter.fromCreateOrUpdateDto(createOrUpdateSpaceShipDto);
            spaceShip.setId(id);
            return SpaceShipConverter.toSpaceShipDetailsDto(spaceShipRepository.save(spaceShip));
        }
        throw new SpaceShipNotFound(String.format(ErrorMessages.SPACESHIP_NOT_FOUND,id));
    }
}
