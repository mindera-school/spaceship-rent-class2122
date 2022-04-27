package com.mindera.school.spaceshiprent.service.rent;

import com.mindera.school.spaceshiprent.converter.RentConverter;
import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.exception.exceptions.RentAlreadyPickedUpException;
import com.mindera.school.spaceshiprent.exception.exceptions.RentAlreadyReturnedException;
import com.mindera.school.spaceshiprent.exception.exceptions.RentNotPickedUpException;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.exception.ErrorMessageConstants;
import com.mindera.school.spaceshiprent.exception.exceptions.RentNotFoundException;
import com.mindera.school.spaceshiprent.exception.exceptions.SpaceshipNotFoundException;
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

import static com.mindera.school.spaceshiprent.util.LoggerHelper.COULD_NOT_FIND;
import static com.mindera.school.spaceshiprent.util.LoggerHelper.CUSTOMER;
import static com.mindera.school.spaceshiprent.util.LoggerHelper.RENT;
import static com.mindera.school.spaceshiprent.util.LoggerHelper.RENT_ALREADY_PICKED_UP;
import static com.mindera.school.spaceshiprent.util.LoggerHelper.RENT_ALREADY_RETURNED;
import static com.mindera.school.spaceshiprent.util.LoggerHelper.RENT_NOT_PICKED_UP;
import static com.mindera.school.spaceshiprent.util.LoggerHelper.SPACESHIP;
import static com.mindera.school.spaceshiprent.util.LoggerHelper.newLogMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public final class RentServiceImpl implements RentService {

    private final RentConverter converter;
    private final RentRepository rentRepository;
    private final UserRepository userRepository;
    private final SpaceshipRepository spaceShipRepository;

    @Override
    public RentDetailsDto createRent(final CreateOrUpdateRentDto rentDto) {
        final var rent = converter.convertToEntity(rentDto);

        rent.setUserEntity(getCustomerEntityById(rentDto.getCustomerId()));
        rent.setSpaceshipEntity(getSpaceshipEntityById(rentDto.getSpaceshipId()));
        rent.setPricePerDay(rent.getSpaceshipEntity().getPriceDay());

        return converter.convertToRentDetailsDto(rentRepository.save(rent));
    }

    @Override
    public List<RentDetailsDto> getAllRents() {
        return rentRepository.findAll().stream()
                .map(converter::convertToRentDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    public RentDetailsDto getRentById(final Long rentId) {
        return converter.convertToRentDetailsDto(getRentEntityById(rentId));
    }

    @Override
    public RentDetailsDto updateRent(final Long rentId, final CreateOrUpdateRentDto createOrUpdateRentDto) {
        final var rentEntity = getRentEntityById(rentId);

        final var rent = converter.convertToEntity(createOrUpdateRentDto);
        rent.setId(rentId);
        rent.setSpaceshipEntity(rentEntity.getSpaceshipEntity());
        rent.setUserEntity(rentEntity.getUserEntity());
        rent.setPricePerDay(rentEntity.getPricePerDay());

        return converter.convertToRentDetailsDto(
                rentRepository.save(rent));
    }

    @Override
    public List<RentDetailsDto> getRentsByCustomerId(final Long customerId) {
        getCustomerEntityById(customerId);
        return rentRepository.findByUserId(customerId).stream()
                .map(converter::convertToRentDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RentDetailsDto> getRentsBySpaceshipId(final Long spaceshipId) {
        getSpaceshipEntityById(spaceshipId);
        return rentRepository.findBySpaceshipId(spaceshipId).stream()
                .map(converter::convertToRentDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    public RentDetailsDto pickupRent(final Long userId, final Long rentId) {
        final var rentEntity = rentRepository.findByIdAndUserId(rentId, userId)
                .orElseThrow(() -> {
                    log.error(newLogMessage()
                            .rentId(rentId)
                            .userId(userId)
                            .message(COULD_NOT_FIND, RENT)
                            .build());
                    return new RentNotFoundException(
                            String.format(ErrorMessageConstants.RENT_NOT_FOUND, rentId));
                });

        if (rentEntity.getPickupDate() != null) {
            log.error(newLogMessage()
                    .rentId(rentId)
                    .userId(userId)
                    .message(RENT_ALREADY_PICKED_UP)
                    .build());
            throw new RentAlreadyPickedUpException(RENT_ALREADY_PICKED_UP);
        }

        rentEntity.setPickupDate(LocalDate.now());

        return converter.convertToRentDetailsDto(rentEntity);
    }

    @Override
    public RentDetailsDto returnRent(final Long userId, final Long rentId) {
        final var rentEntity = rentRepository.findByIdAndUserId(rentId, userId)
                .orElseThrow(() -> {
                    log.error(newLogMessage()
                            .rentId(rentId)
                            .message(COULD_NOT_FIND, RENT)
                            .build());
                    return new RentNotFoundException(
                            String.format(ErrorMessageConstants.RENT_NOT_FOUND, rentId));
                });

        if (rentEntity.getPickupDate() == null) {
            log.error(newLogMessage()
                    .rentId(rentId)
                    .userId(userId)
                    .message(RENT_NOT_PICKED_UP)
                    .build());
            throw new RentNotPickedUpException(RENT_NOT_PICKED_UP);
        }

        if (rentEntity.getReturnDate() != null) {
            log.error(newLogMessage()
                    .rentId(rentId)
                    .userId(userId)
                    .message(RENT_ALREADY_RETURNED)
                    .build());
            throw new RentAlreadyReturnedException(RENT_ALREADY_RETURNED);
        }

        rentEntity.setReturnDate(LocalDate.now());

        return converter.convertToRentDetailsDto(rentEntity);
    }

    private RentEntity getRentEntityById(final Long rentId) {
        return rentRepository.findById(rentId)
                .orElseThrow(() -> {
                    log.error(newLogMessage()
                            .rentId(rentId)
                            .message(COULD_NOT_FIND, RENT)
                            .build());
                    return new RentNotFoundException(
                            String.format(ErrorMessageConstants.RENT_NOT_FOUND, rentId));
                });
    }

    private UserEntity getCustomerEntityById(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error(newLogMessage()
                            .customerId(userId)
                            .message(COULD_NOT_FIND, CUSTOMER)
                            .build());
                    return new UserNotFoundException(String.format(ErrorMessageConstants.USER_NOT_FOUND, userId));
                });
    }

    private SpaceshipEntity getSpaceshipEntityById(final Long spaceshipId) {
        return spaceShipRepository.findById(spaceshipId)
                .orElseThrow(() -> {
                    log.error(newLogMessage()
                            .spaceshipId(spaceshipId)
                            .message(COULD_NOT_FIND, SPACESHIP)
                            .build());
                    return new SpaceshipNotFoundException(String.format(ErrorMessageConstants.SPACESHIP_NOT_FOUND, spaceshipId));
                });
    }
}
