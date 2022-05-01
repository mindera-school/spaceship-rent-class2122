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

    @PostMapping("/rents")
    public ResponseEntity<RentDetailsDto> createRent(@RequestBody @Valid CreateOrUpdateRentDto dto) {
        LOGGER.info(LoggerMessages.POST_REQUEST, LoggerMessages.RENT);
        return ResponseEntity.ok(rentService.createRent(dto));
    }

    @GetMapping("/rents")
    public ResponseEntity<List<RentDetailsDto>> getAllRents() {
        LOGGER.info(LoggerMessages.GET_ALL_REQUEST, LoggerMessages.RENT);
        return ResponseEntity.ok(rentService.getAllRents());
    }

    @GetMapping("/rents/{id}")
    public ResponseEntity<RentDetailsDto> getRentById(@PathVariable Long id) {
        LOGGER.info(LoggerMessages.GET_REQUEST, LoggerMessages.RENT);
        return ResponseEntity.ok(rentService.getRentById(id));
    }

    @GetMapping("/customers/{customerId}/rents")
    public ResponseEntity<List<RentDetailsDto>> getRentByCostumerId(@PathVariable Long costumerId) {
        LOGGER.info(LoggerMessages.GET_REQUEST, "rents by customer id");
        return ResponseEntity.ok(rentService.getRentByCustomerId(costumerId));
    }

    @GetMapping("/spaceships/{spaceshipId}/rents")
    public ResponseEntity<List<RentDetailsDto>> getRentBySpaceshipId(@PathVariable Long spaceshipId) {
        LOGGER.info(LoggerMessages.GET_REQUEST, "rents by user id");
        return ResponseEntity.ok(rentService.getRentBySpaceShipId(spaceshipId));
    }

    @PutMapping("/rents/{id}")
    public ResponseEntity<RentDetailsDto> updateRentById(
            @PathVariable Long id,
            @RequestBody @Valid CreateOrUpdateRentDto dto) {
        LOGGER.info(LoggerMessages.PUT_REQUEST, LoggerMessages.RENT);
        return ResponseEntity.ok(rentService.updateRent(id, dto));
    }
}



