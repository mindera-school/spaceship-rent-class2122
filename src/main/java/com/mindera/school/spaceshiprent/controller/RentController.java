package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.service.rentService.RentService;
import com.mindera.school.spaceshiprent.util.LoggerMessages;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rents")
public class RentController {

    private final Logger LOGGER = LoggerFactory.getLogger(RentController.class);
    private final RentService rentService;

    @PostMapping
    public ResponseEntity<RentDetailsDto> createRent(@RequestBody @Valid CreateOrUpdateRentDto dto) {
        LOGGER.info(LoggerMessages.POST_REQUEST, LoggerMessages.RENT, LocalDate.now());
        return ResponseEntity.ok(rentService.createRent(dto));
    }

    @GetMapping
    public ResponseEntity<List<RentDetailsDto>> getAllRents() {
        LOGGER.info(LoggerMessages.GET_ALL_REQUEST, LoggerMessages.RENT, LocalDate.now());
        return ResponseEntity.ok(rentService.getAllRents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentDetailsDto> getRentById(@PathVariable Long id) {
        LOGGER.info(LoggerMessages.GET_REQUEST, LoggerMessages.RENT, LocalDate.now());
        return ResponseEntity.ok(rentService.getRentById(id));
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<List<RentDetailsDto>> getRentByCostumerId(@PathVariable Long costumerId) {
        LOGGER.info(LoggerMessages.GET_REQUEST, "rents by customer id", LocalDate.now());
        return ResponseEntity.ok(rentService.getRentByCustomerId(costumerId));
    }

    @GetMapping("/spaceships/{spaceshipId}")
    public ResponseEntity<List<RentDetailsDto>> getRentBySpaceshipId(@PathVariable Long spaceshipId) {
        LOGGER.info(LoggerMessages.GET_REQUEST, "rents by user id", LocalDate.now());
        return ResponseEntity.ok(rentService.getRentBySpaceShipId(spaceshipId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentDetailsDto> updateRentById(
            @PathVariable Long id,
            @RequestBody @Valid CreateOrUpdateRentDto dto) {
        LOGGER.info(LoggerMessages.PUT_REQUEST, LoggerMessages.RENT, LocalDate.now());
        return ResponseEntity.ok(rentService.updateRent(id, dto));
    }
}



