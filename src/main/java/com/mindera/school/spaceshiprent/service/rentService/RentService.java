package com.mindera.school.spaceshiprent.service.rentService;

import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;

import java.util.List;

public interface RentService {

    RentDetailsDto createRent(CreateOrUpdateRentDto createOrUpdateRentDto);

    List<RentDetailsDto> getAllRents();

    RentDetailsDto getRentById(Long id);

    RentDetailsDto updateRent (Long id, CreateOrUpdateRentDto createOrUpdateRentDto);

    List<RentDetailsDto> getRentByCustomerId (Long id);

    List<RentDetailsDto> getRentBySpaceShipId (Long id);

    RentDetailsDto updatePickUpDate (Long id);

    RentDetailsDto updateReturnDate (Long id);

}
