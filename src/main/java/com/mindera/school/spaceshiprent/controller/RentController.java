package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.service.rent.RentService;
import com.mindera.school.spaceshiprent.util.LoggerMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.mindera.school.spaceshiprent.controller.Paths.PATH_GET_RENTS;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_GET_RENTS_BY_CUSTOMER;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_GET_RENTS_BY_SPACESHIP;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_GET_RENT_BY_ID;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_POST_RENT;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_UPDATE_PICK_UP_DATE;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_UPDATE_RENT_BY_ID;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_UPDATE_RETURN_DATE;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RentController {

    private final RentService rentService;

    @PostMapping(PATH_POST_RENT)
    public ResponseEntity<RentDetailsDto> createRent(@RequestBody @Valid CreateOrUpdateRentDto dto) {
        log.info(LoggerMessages.POST_REQUEST, LoggerMessages.RENT);
        return ResponseEntity.ok(rentService.createRent(dto));
    }

    @GetMapping(PATH_GET_RENTS)
    public ResponseEntity<List<RentDetailsDto>> getAllRents() {
        log.info(LoggerMessages.GET_ALL_REQUEST, LoggerMessages.RENT);
        return ResponseEntity.ok(rentService.getAllRents());
    }

    @GetMapping(PATH_GET_RENT_BY_ID)
    public ResponseEntity<RentDetailsDto> getRentById(@PathVariable Long id) {
        log.info(LoggerMessages.GET_REQUEST, LoggerMessages.RENT);
        return ResponseEntity.ok(rentService.getRentById(id));
    }

    @GetMapping(PATH_GET_RENTS_BY_CUSTOMER)
    public ResponseEntity<List<RentDetailsDto>> getRentByCostumerId(@PathVariable Long customerId) {
        log.info(LoggerMessages.GET_REQUEST, "rents by customer id");
        return ResponseEntity.ok(rentService.getRentByCustomerId(customerId));
    }

    @GetMapping(PATH_GET_RENTS_BY_SPACESHIP)
    public ResponseEntity<List<RentDetailsDto>> getRentBySpaceshipId(@PathVariable Long spaceshipId) {
        log.info(LoggerMessages.GET_REQUEST, "rents by spaceship id");
        return ResponseEntity.ok(rentService.getRentBySpaceShipId(spaceshipId));
    }

    @PutMapping(PATH_UPDATE_RENT_BY_ID)
    public ResponseEntity<RentDetailsDto> updateRentById(
            @PathVariable Long id,
            @RequestBody @Valid CreateOrUpdateRentDto dto) {
        log.info(LoggerMessages.PUT_REQUEST, LoggerMessages.RENT);
        return ResponseEntity.ok(rentService.updateRent(id, dto));
    }

    @PutMapping(PATH_UPDATE_PICK_UP_DATE)
    public ResponseEntity<RentDetailsDto> updatePickUpDate(@PathVariable Long id) {
        log.info(LoggerMessages.PUT_REQUEST, LoggerMessages.RENT);
        return ResponseEntity.ok(rentService.updatePickUpDate(id));
    }

    @PutMapping(PATH_UPDATE_RETURN_DATE)
    public ResponseEntity<RentDetailsDto> updateReturnDate(@PathVariable Long id) {
        log.info(LoggerMessages.PUT_REQUEST, LoggerMessages.RENT);
        return ResponseEntity.ok(rentService.updateReturnDate(id));
    }
}



