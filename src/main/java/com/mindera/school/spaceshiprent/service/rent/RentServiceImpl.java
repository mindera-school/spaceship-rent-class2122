package com.mindera.school.spaceshiprent.service.rent;

import com.mindera.school.spaceshiprent.converter.RentConverter;
import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentServiceImpl implements RentService {

    private final RentConverter converter;
    private final RentRepository rentRepository;
    private final UserRepository userRepository;
    private final SpaceshipRepository spaceShipRepository;

    @Override
    public RentDetailsDto createRent(CreateOrUpdateRentDto rentDto) {
        RentEntity rent = converter.convertToEntity(rentDto);
         log.info(rent.toString());
         rent.setUserEntity(userRepository.findById(rentDto.getCustomerId())
                .orElseThrow(() -> {
                    log.info("Rent Created with invalid user");
                    return new UserNotFoundException(String.format(ErrorMessageConstants.USER_NOT_FOUND,
                    rentDto.getCustomerId()
                    ));})
        );

        rent.setSpaceShipEntity(spaceShipRepository.findById(rentDto.getSpaceshipId())
                .orElseThrow(() -> {
                    log.info("Rent Created with invalid spaceship");
                    return new SpaceshipNotFoundException(String.format(ErrorMessageConstants.SPACESHIP_NOT_FOUND,
                            rentDto.getSpaceshipId())
                    );})

        );

          rent.setPricePerDay(rent.getSpaceShipEntity().getPriceDay());
          RentEntity savedRent = rentRepository.save(rent);
          log.info("Rent Created");
          log.info(converter.convertToRentDetailsDto(savedRent).toString());
          return converter.convertToRentDetailsDto(savedRent);
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

        Optional<RentEntity> rentEntityOptional = rentRepository.findById(id);

        if (rentEntityOptional.isPresent()) {
            RentEntity rent = converter.convertToEntity(createOrUpdateRentDto);
            rent.setId(id);
            rent.setSpaceShipEntity(rentEntityOptional.get().getSpaceShipEntity());
            rent.setUserEntity(rentEntityOptional.get().getUserEntity());
            rent.setPricePerDay(rentEntityOptional.get().getPricePerDay());
            return converter.convertToRentDetailsDto(rentRepository.save(rent));
        }

        throw new RentNotFoundException(String.format(ErrorMessageConstants.RENT_NOT_FOUND, id));
    }

    @Override
    public List<RentDetailsDto> getRentByCustomerId(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new RentNotFoundException(String.format(ErrorMessageConstants.USER_NOT_FOUND, id)))
                .getRentEntity()
                .stream()
                .map(converter::convertToRentDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RentDetailsDto> getRentBySpaceShipId(Long id) {

        return spaceShipRepository.findById(id)
                .orElseThrow(() -> new RentNotFoundException(String.format(ErrorMessageConstants.SPACESHIP_NOT_FOUND, id)))
                .getRentEntity()
                .stream()
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

        throw new RentNotFoundException(String.format(ErrorMessageConstants.RENT_NOT_FOUND, id));
    }

    @Override
    public RentDetailsDto updateReturnDate(Long id) {

        Optional<RentEntity> rentEntity = rentRepository.findById(id);

        if (rentEntity.isPresent()) {
            rentEntity.get().setReturnDate(LocalDate.now());
            return converter.convertToRentDetailsDto(rentEntity.get());
        }

        throw new RentNotFoundException(String.format(ErrorMessageConstants.RENT_NOT_FOUND, id));
    }
}
