package com.mindera.school.spaceshiprent.service.rentService;

import com.mindera.school.spaceshiprent.converter.RentConverter;
import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceShipEntity;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.RentRepository;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceShipRepository;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentServiceImpl implements RentService {

    private final RentRepository rentRepository;
    private final UserRepository userRepository;
    private final SpaceShipRepository spaceShipRepository;

    @Override
    public RentDetailsDto createRent(CreateOrUpdateRentDto createOrUpdateRentDto) {
        RentEntity rent = RentConverter.fromCreateOrUpdateRentDto(createOrUpdateRentDto);
        UserEntity user = userRepository.findById(createOrUpdateRentDto.getCustomerId()).orElse(null);
        SpaceShipEntity spaceShip = spaceShipRepository.findById(createOrUpdateRentDto.getSpaceshipId()).orElse(null);
        rent.setUserEntity(user);
        rent.setSpaceShipEntity(spaceShip);
        return RentConverter.toRentDetailsDto(rentRepository.save(rent));
    }

    @Override
    public List<RentDetailsDto> getAllRents() {
        return rentRepository.findAll().stream()
                .map(RentConverter::toRentDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    public RentDetailsDto getRentById(Long id) {
        Optional<RentEntity> rentEntityOptional = rentRepository.findById(id);
        return rentEntityOptional.map(RentConverter::toRentDetailsDto).orElse(null);
    }

    @Override
    public RentDetailsDto updateRent(Long id, CreateOrUpdateRentDto createOrUpdateRentDto) {
        Optional<RentEntity> rentEntityOptional = rentRepository.findById(id);
        if (rentEntityOptional.isPresent()) {
            RentEntity rent = RentConverter.fromCreateOrUpdateRentDto(createOrUpdateRentDto);
            rent.setId(id);
            return RentConverter.toRentDetailsDto(rentRepository.save(rent));
        }
        return null;
    }

    @Override
    public List<RentDetailsDto> getRentByCustomerId(Long id) {
        List<RentEntity> rentEntity = userRepository.findById(id).orElse(null).getRentEntity();
        return rentEntity.stream()
                .map(RentConverter::toRentDetailsDto)
                .collect(Collectors.toList())
                ;
    }

    @Override
    public List<RentDetailsDto> getRentBySpaceShipId(Long id) {
        List<RentEntity> rentEntity = spaceShipRepository.findById(id).orElse(null).getRentEntity();
        return rentEntity.stream()
                .map(RentConverter::toRentDetailsDto)
                .collect(Collectors.toList())
                ;
    }

    @Override
    public RentDetailsDto updatePickUpDate(Long id) {
        Optional<RentEntity> rentEntity = rentRepository.findById(id);
        if(rentEntity.isPresent()) {
            rentEntity.get().setPickupDate(LocalDate.now());
            return RentConverter.toRentDetailsDto(rentEntity.get());
        }
        return null;
    }

    @Override
    public RentDetailsDto updateReturnDate(Long id) {
        Optional<RentEntity> rentEntity = rentRepository.findById(id);
        if(rentEntity.isPresent()) {
            rentEntity.get().setReturnDate(LocalDate.now());
            return RentConverter.toRentDetailsDto(rentEntity.get());
        }
        return null;
    }
}
