package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceShipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.service.spaceShipService.SpaceShipServiceImpl;
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
@RequestMapping("/spaceships")
public class SpaceShipController {

    private final Logger LOGGER = LoggerFactory.getLogger(SpaceShipController.class);
    private final SpaceShipServiceImpl spaceShipService;

    @PostMapping
    public ResponseEntity<SpaceShipDetailsDto> createSpaceship(@RequestBody @Valid CreateOrUpdateSpaceShipDto dto) {
        LOGGER.info(LoggerMessages.POST_REQUEST, LoggerMessages.SPACESHIP, LocalDate.now());
        return ResponseEntity.ok(spaceShipService.createSpaceShip(dto));
    }

    @GetMapping
    public ResponseEntity<List<SpaceShipDetailsDto>> getAllSpaceShips() {
        LOGGER.info(LoggerMessages.GET_ALL_REQUEST, LoggerMessages.SPACESHIP, LocalDate.now());
        return ResponseEntity.ok(spaceShipService.getAllSpaceShips());
    }

    @GetMapping("{id}")
    public ResponseEntity<SpaceShipDetailsDto> getSpaceShipById(@PathVariable Long id) {
        LOGGER.info(LoggerMessages.GET_REQUEST, LoggerMessages.SPACESHIP, LocalDate.now());
        return ResponseEntity.ok(spaceShipService.getSpaceShipById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<SpaceShipDetailsDto> updateSpaceshipById(
            @PathVariable Long id,
            @RequestBody @Valid CreateOrUpdateSpaceShipDto dto) {
        LOGGER.info(LoggerMessages.PUT_REQUEST, LoggerMessages.SPACESHIP, LocalDate.now());
        return ResponseEntity.ok(spaceShipService.updateSpaceShipById(id, dto));
    }

}
