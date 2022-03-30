package com.mindera.school.spaceshiprent.service.rentService;

import com.mindera.school.spaceshiprent.converter.RentConverter;
import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.exception.ErrorMessages;
import com.mindera.school.spaceshiprent.exception.NotFoundExceptions.RentNotFoundException;
import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
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
    public RentDetailsDto createRent(CreateOrUpdateRentDto rentDto) {
        RentEntity rent = RentConverter.fromCreateOrUpdateRentDto(rentDto);

        rent.setUserEntity(userRepository.findById(rentDto.getCustomerId())
                .orElseThrow(() -> new RentNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND, rentDto.getCustomerId())))
        );

        rent.setSpaceShipEntity(spaceShipRepository.findById(rentDto.getSpaceshipId())
                .orElseThrow(() -> new RentNotFoundException(String.format(ErrorMessages.SPACESHIP_NOT_FOUND, rentDto.getSpaceshipId())))
        );

        rent.setPricePerDay(rent.getSpaceShipEntity().getPriceDay());

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
        return RentConverter.toRentDetailsDto(rentRepository.findById(id)
                .orElseThrow(() -> new RentNotFoundException(String.format(ErrorMessages.RENT_NOT_FOUND, id)))
        );
    }

    @Override
    public RentDetailsDto updateRent(Long id, CreateOrUpdateRentDto createOrUpdateRentDto) {
        Optional<RentEntity> rentEntityOptional = rentRepository.findById(id);
        if (rentEntityOptional.isPresent()) {
            RentEntity rent = RentConverter.fromCreateOrUpdateRentDto(createOrUpdateRentDto);
            rent.setId(id);
            return RentConverter.toRentDetailsDto(rentRepository.save(rent));
        }
        throw new RentNotFoundException(String.format(ErrorMessages.RENT_NOT_FOUND, id));
    }

    @Override
    public List<RentDetailsDto> getRentByCustomerId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RentNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND, id)))
                .getRentEntity()
                .stream()
                .map(RentConverter::toRentDetailsDto)
                .collect(Collectors.toList())
                ;
    }

    @Override
    public List<RentDetailsDto> getRentBySpaceShipId(Long id) {
        return spaceShipRepository.findById(id)
                .orElseThrow(() -> new RentNotFoundException(String.format(ErrorMessages.SPACESHIP_NOT_FOUND, id)))
                .getRentEntity()
                .stream()
                .map(RentConverter::toRentDetailsDto)
                .collect(Collectors.toList())
                ;
    }

    @Override
    public RentDetailsDto updatePickUpDate(Long id) {
        Optional<RentEntity> rentEntity = rentRepository.findById(id);
        if (rentEntity.isPresent()) {
            rentEntity.get().setPickupDate(LocalDate.now());
            return RentConverter.toRentDetailsDto(rentEntity.get());
        }
        throw new RentNotFoundException(String.format(ErrorMessages.RENT_NOT_FOUND, id));
    }

    @Override
    public RentDetailsDto updateReturnDate(Long id) {
        Optional<RentEntity> rentEntity = rentRepository.findById(id);
        if (rentEntity.isPresent()) {
            rentEntity.get().setReturnDate(LocalDate.now());
            return RentConverter.toRentDetailsDto(rentEntity.get());
        }
        throw new RentNotFoundException(String.format(ErrorMessages.RENT_NOT_FOUND, id));
    }
}
