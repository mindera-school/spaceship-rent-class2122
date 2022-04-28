package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.service.rent.RentService;
import com.mindera.school.spaceshiprent.util.LoggerMessages;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RentController {

    private final Logger LOGGER = LoggerFactory.getLogger(RentController.class);
    private final RentService rentService;

    public static final String PATH_CREATE_RENT = "spaceship-rent/api/rents";
    public static final String PATH_GET_RENT = "spaceship-rent/api/rents";
    public static final String PATH_GET_RENT_BY_ID = "spaceship-rent/api/rents/{id}";
    public static final String PATH_UPDATE_RENT_BY_ID = "spaceship-rent/api/rents/{id}";
    public static final String PATH_GET_RENT_BY_CUSTOMER_ID = "spaceship-rent/customers/{customerId}/rents";
    public static final String PATH_GET_RENT_BY_SPACESHIP_ID = "/spaceships/{spaceshipId}/rents";

    @PostMapping(PATH_CREATE_RENT)
    public ResponseEntity<RentDetailsDto> createRent(@RequestBody @Valid CreateOrUpdateRentDto dto) {
        LOGGER.info(LoggerMessages.POST_REQUEST, LoggerMessages.RENT, LocalDate.now());
        return ResponseEntity.ok(rentService.createRent(dto));
    }

    @GetMapping(PATH_GET_RENT)
    public ResponseEntity<List<RentDetailsDto>> getAllRents() {
        LOGGER.info(LoggerMessages.GET_ALL_REQUEST, LoggerMessages.RENT, LocalDate.now());
        return ResponseEntity.ok(rentService.getAllRents());
    }

    @GetMapping(PATH_GET_RENT_BY_ID)
    public ResponseEntity<RentDetailsDto> getRentById(@PathVariable Long id) {
        LOGGER.info(LoggerMessages.GET_REQUEST, LoggerMessages.RENT, LocalDate.now());
        return ResponseEntity.ok(rentService.getRentById(id));
    }

    @GetMapping(PATH_GET_RENT_BY_CUSTOMER_ID)
    public ResponseEntity<List<RentDetailsDto>> getRentByCostumerId(@PathVariable Long costumerId) {
        LOGGER.info(LoggerMessages.GET_REQUEST, "rents by customer id", LocalDate.now());
        return ResponseEntity.ok(rentService.getRentByCustomerId(costumerId));
    }

    @GetMapping(PATH_GET_RENT_BY_SPACESHIP_ID)
    public ResponseEntity<List<RentDetailsDto>> getRentBySpaceshipId(@PathVariable Long spaceshipId) {
        LOGGER.info(LoggerMessages.GET_REQUEST, "rents by user id", LocalDate.now());
        return ResponseEntity.ok(rentService.getRentBySpaceShipId(spaceshipId));
    }

    @PutMapping(PATH_UPDATE_RENT_BY_ID)
    public ResponseEntity<RentDetailsDto> updateRentById(
            @PathVariable Long id,
            @RequestBody @Valid CreateOrUpdateRentDto dto) {
        LOGGER.info(LoggerMessages.PUT_REQUEST, LoggerMessages.RENT, LocalDate.now());
        return ResponseEntity.ok(rentService.updateRent(id, dto));
    }
}



