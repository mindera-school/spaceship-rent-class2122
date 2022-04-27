package com.mindera.school.spaceshiprent.service.rent;

import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;

import java.util.List;

public interface RentService {

    RentDetailsDto createRent(CreateOrUpdateRentDto createOrUpdateRentDto);

    List<RentDetailsDto> getAllRents();

    RentDetailsDto getRentById(Long id);

    RentDetailsDto updateRent (Long id, CreateOrUpdateRentDto createOrUpdateRentDto);

    List<RentDetailsDto> getRentsByCustomerId(Long id);

    List<RentDetailsDto> getRentsBySpaceshipId(Long id);

    RentDetailsDto pickupRent (Long userId, Long id);

    RentDetailsDto returnRent (Long userId, Long id);

}
