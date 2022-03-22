package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.service.rentService.RentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rents")
public class RentController {

    private final RentService rentService;
    public static final Logger LOGGER = LoggerFactory.getLogger(RentController.class);


    @PostMapping
    public ResponseEntity<RentDetailsDto> createRent(@RequestBody CreateOrUpdateRentDto dto) {
        LOGGER.info("Created rent, {}",dto);
        LOGGER.warn("Warning");
        LOGGER.error("Error fatal");
        return ResponseEntity.ok(rentService.createRent(dto));
    }

    @GetMapping
    public ResponseEntity<List<RentDetailsDto>> getAllRents() {
        return ResponseEntity.ok(rentService.getAllRents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentDetailsDto> getRentById(@PathVariable Long id){
        LOGGER.info("userRequest, {}",id);
        LOGGER.warn("Warning");
        LOGGER.error("Error fatal");
        return ResponseEntity.ok(rentService.getRentById(id));
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<List<RentDetailsDto>> getRentByCostumerId(@PathVariable Long costumerId){
        LOGGER.info("userRequest, {}",costumerId);
        LOGGER.warn("Warning");
        LOGGER.error("Error fatal");
        return ResponseEntity.ok(rentService.getRentByCustomerId(costumerId));
    }

    @GetMapping("/spaceships/{spaceshipId}")
    public ResponseEntity<List<RentDetailsDto>> getRentBySpaceshipId(@PathVariable Long spaceshipId){
        LOGGER.info("userRequest, {}",spaceshipId);
        LOGGER.warn("Warning");
        LOGGER.error("Error fatal");
        return ResponseEntity.ok(rentService.getRentBySpaceShipId(spaceshipId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentDetailsDto> updateRentById(@PathVariable Long id, @RequestBody CreateOrUpdateRentDto dto) {
        LOGGER.info("userRequest, {}",dto);
        LOGGER.warn("Warning");
        LOGGER.error("Error fatal");
        return ResponseEntity.ok(rentService.updateRent(id,dto));
    }
}



