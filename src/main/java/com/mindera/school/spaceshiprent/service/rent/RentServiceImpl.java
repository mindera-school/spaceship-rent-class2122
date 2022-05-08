package com.mindera.school.spaceshiprent.service.rent;

import com.mindera.school.spaceshiprent.converter.RentConverter;
import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.exception.ErrorMessageConstants;
import com.mindera.school.spaceshiprent.exception.exceptions.RentNotFoundException;
import com.mindera.school.spaceshiprent.exception.exceptions.SpaceshipNotFoundException;
import com.mindera.school.spaceshiprent.exception.exceptions.UnavailableRentDatesException;
import com.mindera.school.spaceshiprent.exception.exceptions.UserNotFoundException;
import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
import com.mindera.school.spaceshiprent.persistence.repository.RentRepository;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceshipRepository;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindera.school.spaceshiprent.exception.ErrorMessageConstants.SPACESHIP_NOT_FOUND;
import static com.mindera.school.spaceshiprent.exception.ErrorMessageConstants.SPACESHIP_UNAVAILABLE;
import static com.mindera.school.spaceshiprent.exception.ErrorMessageConstants.USER_NOT_FOUND;
import static com.mindera.school.spaceshiprent.util.LoggerMessages.CREATED;
import static com.mindera.school.spaceshiprent.util.LoggerMessages.RENT;
import static com.mindera.school.spaceshiprent.util.LoggerMessages.RENT_PICK_UP;
import static com.mindera.school.spaceshiprent.util.LoggerMessages.RENT_RETURN;
import static com.mindera.school.spaceshiprent.util.LoggerMessages.UPDATED;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentServiceImpl implements RentService {

    private final RentConverter converter;
    private final RentRepository rentRepository;
    private final UserRepository userRepository;
    private final SpaceshipRepository spaceShipRepository;

    @Override
    public RentDetailsDto createRent(CreateOrUpdateRentDto rentDto) {

        RentEntity rent = converter.convertToEntity(rentDto);

        rent.setUserEntity(userRepository.findById(rentDto.getCustomerId())
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND,
                        rentDto.getCustomerId()))));

        rent.setSpaceShipEntity(spaceShipRepository.findById(rentDto.getSpaceshipId())
                .orElseThrow(() -> new SpaceshipNotFoundException(String.format(SPACESHIP_NOT_FOUND,
                        rentDto.getSpaceshipId()))));

        final boolean isSpaceshipAvailable = rentRepository.checkRentAvailability(rent.getExpectedPickupDate(),
                rent.getExpectedReturnDate(),
                rent.getSpaceShipEntity().getId()
        );

        if (!isSpaceshipAvailable)
            throw new UnavailableRentDatesException(String.format(SPACESHIP_UNAVAILABLE,
                    rent.getSpaceShipEntity().getId(),
                    rent.getExpectedPickupDate(),
                    rent.getExpectedReturnDate()));

        rent.setPricePerDay(rent.getSpaceShipEntity().getPriceDay());

        RentEntity savedEntity = rentRepository.save(rent);
        log.info(CREATED, RENT);

        return converter.convertToRentDetailsDto(savedEntity);
    }

    @Override
    public List<RentDetailsDto> getAllRents() {

        return rentRepository.findAll().stream()
                .map(converter::convertToRentDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    public RentDetailsDto getRentById(Long id) {
        return converter.convertToRentDetailsDto(rentRepository.findById(id)
                .orElseThrow(() -> new RentNotFoundException(String.format(ErrorMessageConstants.RENT_NOT_FOUND, id)))
        );
    }

    @Override
    public RentDetailsDto updateRent(Long id, CreateOrUpdateRentDto createOrUpdateRentDto) {

        RentEntity rent = rentRepository.findById(id)
                .orElseThrow(() -> new RentNotFoundException(String.format(ErrorMessageConstants.RENT_NOT_FOUND, id)));

        final boolean isSpaceshipAvailable = rentRepository.checkRentAvailability(createOrUpdateRentDto.getExpectedPickupDate(),
                createOrUpdateRentDto.getExpectedReturnDate(),
                createOrUpdateRentDto.getSpaceshipId()
        );

        if (!isSpaceshipAvailable)
            throw new UnavailableRentDatesException(String.format(SPACESHIP_UNAVAILABLE,
                    createOrUpdateRentDto.getSpaceshipId(),
                    createOrUpdateRentDto.getExpectedPickupDate(),
                    createOrUpdateRentDto.getExpectedReturnDate()));

        rent.setExpectedPickupDate(createOrUpdateRentDto.getExpectedPickupDate());
        rent.setExpectedReturnDate(createOrUpdateRentDto.getExpectedReturnDate());
        rent.setDiscount(createOrUpdateRentDto.getDiscount());

        return converter.convertToRentDetailsDto(rentRepository.save(rent));
    }

    @Override
    public List<RentDetailsDto> getRentByCustomerId(Long id)    {

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND, id)))
                .getRentEntity()
                .stream()
                .map(converter::convertToRentDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RentDetailsDto> getRentBySpaceShipId(Long id) {

        return spaceShipRepository.findById(id)
                .orElseThrow(() -> new SpaceshipNotFoundException(String.format(SPACESHIP_NOT_FOUND, id)))
                .getRentEntity()
                .stream()
                .map(converter::convertToRentDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    public RentDetailsDto updatePickUpDate(Long id) {

        RentEntity rentEntity = rentRepository.findById(id)
                .orElseThrow(() -> new RentNotFoundException(String.format(ErrorMessageConstants.RENT_NOT_FOUND, id)));

        rentEntity.setPickupDate(LocalDate.now());

        log.info(UPDATED, RENT_PICK_UP);
        return converter.convertToRentDetailsDto(rentEntity);
    }

    @Override
    public RentDetailsDto updateReturnDate(Long id) {

        RentEntity rentEntity = rentRepository.findById(id)
                .orElseThrow(() -> new RentNotFoundException(String.format(ErrorMessageConstants.RENT_NOT_FOUND, id)));

        rentEntity.setReturnDate(LocalDate.now());

        log.info(UPDATED, RENT_RETURN);
        return converter.convertToRentDetailsDto(rentEntity);
    }
}
