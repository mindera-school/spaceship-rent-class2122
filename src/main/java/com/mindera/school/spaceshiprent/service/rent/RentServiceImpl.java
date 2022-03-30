package com.mindera.school.spaceshiprent.service.rent;

import com.mindera.school.spaceshiprent.converter.RentConverter;
import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.exception.ErrorMessageConstants;
import com.mindera.school.spaceshiprent.exception.RentNotFoundException;
import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.RentRepository;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceshipRepository;
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

    private final RentConverter converter;
    private final RentRepository rentRepository;
    private final UserRepository userRepository;
    private final SpaceshipRepository spaceShipRepository;

    @Override
    public RentDetailsDto createRent(CreateOrUpdateRentDto createOrUpdateRentDto) {

        UserEntity user = userRepository.findById(createOrUpdateRentDto.getCustomerId()).orElse(null);

        SpaceshipEntity spaceShip = spaceShipRepository
                .findById(createOrUpdateRentDto.getSpaceshipId())
                .orElseThrow(() -> new RentNotFoundException(String.format(ErrorMessageConstants.RENT_NOT_FOUND,createOrUpdateRentDto)));

        RentEntity rent = converter.convertToEntity(createOrUpdateRentDto);
        rent.setUserEntity(user);
        rent.setSpaceShipEntity(spaceShip);

        return converter.convertToRentDetailsDto(rentRepository.save(rent));
    }

    @Override
    public List<RentDetailsDto> getAllRents() {

        return rentRepository.findAll().stream()
                .map(converter::convertToRentDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    public RentDetailsDto getRentById(Long id) {
        Optional<RentEntity> rentEntityOptional = rentRepository.findById(id);
        return rentEntityOptional.map(converter::convertToRentDetailsDto).orElse(null);
    }

    @Override
    public RentDetailsDto updateRent(Long id, CreateOrUpdateRentDto createOrUpdateRentDto) {
        Optional<RentEntity> rentEntityOptional = rentRepository.findById(id);
        if (rentEntityOptional.isPresent()) {
            RentEntity rent = converter.convertToEntity(createOrUpdateRentDto);
            rent.setId(id);
            return converter.convertToRentDetailsDto(rentRepository.save(rent));
        }

        return null;
    }

    @Override
    public List<RentDetailsDto> getRentByCustomerId(Long id) {
        List<RentEntity> rentEntity = userRepository.findById(id).orElseThrow(() -> new RentNotFoundException(String.format(ErrorMessageConstants.RENT_NOT_FOUND,id)) ).getRentEntity();
        return rentEntity.stream()
                .map(converter::convertToRentDetailsDto)
                .collect(Collectors.toList());

    }

    @Override
    public List<RentDetailsDto> getRentBySpaceShipId(Long id) {
        List<RentEntity> rentEntity = spaceShipRepository.findById(id).orElseThrow(() -> new RentNotFoundException(String.format(ErrorMessageConstants.RENT_NOT_FOUND,id)) ).getRentEntity();
        return rentEntity.stream()
                .map(converter::convertToRentDetailsDto)
                .collect(Collectors.toList())
                ;
    }

    @Override
    public RentDetailsDto updatePickUpDate(Long id) {
        Optional<RentEntity> rentEntity = rentRepository.findById(id);
        if (rentEntity.isPresent()) {
            rentEntity.get().setPickupDate(LocalDate.now());
            return converter.convertToRentDetailsDto(rentEntity.get());
        }

        return null;
    }

    @Override
    public RentDetailsDto updateReturnDate(Long id) {
        Optional<RentEntity> rentEntity = rentRepository.findById(id);
        if (rentEntity.isPresent()) {
            rentEntity.get().setReturnDate(LocalDate.now());
            return converter.convertToRentDetailsDto(rentEntity.get());
        }

        return null;
    }
}
