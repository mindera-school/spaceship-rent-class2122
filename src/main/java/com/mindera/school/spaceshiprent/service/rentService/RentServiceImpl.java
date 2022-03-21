package com.mindera.school.spaceshiprent.service.rentService;

import com.mindera.school.spaceshiprent.converter.RentConverter;
import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.exception.ErrorMessages;
import com.mindera.school.spaceshiprent.exception.RentNotFoundException;
import com.mindera.school.spaceshiprent.exception.SpaceshipNotFoundException;
import com.mindera.school.spaceshiprent.exception.UserNotFoundException;
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
        UserEntity user = userRepository.findById(createOrUpdateRentDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND, createOrUpdateRentDto.getUserId())));
        SpaceShipEntity spaceShip = spaceShipRepository.findById(createOrUpdateRentDto.getSpaceshipId())
                .orElseThrow(() -> new SpaceshipNotFoundException(String.format(ErrorMessages.SPACESHIP_NOT_FOUND, createOrUpdateRentDto.getSpaceshipId())));
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
        return rentEntityOptional.map(RentConverter::toRentDetailsDto)
                .orElseThrow(() -> new RentNotFoundException(String.format(ErrorMessages.RENT_NOT_FOUND, id)));
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
    public List<RentDetailsDto> getRentByUserId(Long id) {
        List<RentEntity> rentEntity = userRepository.findById(id)
                .orElseThrow(() -> new RentNotFoundException(String.format(ErrorMessages.RENT_NOT_FOUND_W_USER, id))).getRentEntity();
        return rentEntity.stream()
                .map(RentConverter::toRentDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RentDetailsDto> getRentBySpaceShipId(Long id) {
        List<RentEntity> rentEntity = spaceShipRepository.findById(id)
                .orElseThrow(() -> new RentNotFoundException(String.format(ErrorMessages.RENT_NOT_FOUND_W_SPACESHIP, id))).getRentEntity();
        return rentEntity.stream()
                .map(RentConverter::toRentDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    public RentDetailsDto updatePickUpDate(Long id) {
        Optional<RentEntity> rentEntity = rentRepository.findById(id);
        rentEntity.orElseThrow(() -> new RentNotFoundException(String.format(ErrorMessages.RENT_NOT_FOUND, id)));

        rentEntity.get().setPickupDate(LocalDate.now());
        return RentConverter.toRentDetailsDto(rentEntity.get());
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
