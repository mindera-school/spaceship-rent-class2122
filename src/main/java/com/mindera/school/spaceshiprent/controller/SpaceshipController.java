package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceshipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.service.spaceship.SpaceShipServiceImpl;
import com.mindera.school.spaceshiprent.util.LoggerMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequiredArgsConstructor
public class SpaceshipController {

    private final SpaceShipServiceImpl spaceShipService;

    @PostMapping("/spaceships")
    public ResponseEntity<SpaceShipDetailsDto> createSpaceship(@RequestBody @Valid CreateOrUpdateSpaceshipDto dto){
        log.info(LoggerMessages.POST_REQUEST, LoggerMessages.SPACESHIP, LocalDate.now());
        return ResponseEntity.ok(spaceShipService.createSpaceShip(dto));
    }

    @GetMapping("/spaceships")
    public ResponseEntity<List<SpaceShipDetailsDto>> getAllSpaceShips(){
        log.info(LoggerMessages.GET_ALL_REQUEST, LoggerMessages.SPACESHIP, LocalDate.now());
        return ResponseEntity.ok(spaceShipService.getAllSpaceShips());
    }

    @GetMapping("/spaceships/{id}")
    public ResponseEntity<SpaceShipDetailsDto> getSpaceShipById(@PathVariable Long id) {
        log.info(LoggerMessages.GET_REQUEST, LoggerMessages.SPACESHIP, LocalDate.now());
        return ResponseEntity.ok(spaceShipService.getSpaceShipById(id));
    }

    @PutMapping("/spaceships/{id}")
    public ResponseEntity<SpaceShipDetailsDto> updateSpaceshipById(@PathVariable Long id,
                                                                   @RequestBody @Valid CreateOrUpdateSpaceshipDto dto){
        log.info(LoggerMessages.PUT_REQUEST, LoggerMessages.SPACESHIP, LocalDate.now());
        return ResponseEntity.ok(spaceShipService.updateSpaceShipById(id,dto));
    }

}
