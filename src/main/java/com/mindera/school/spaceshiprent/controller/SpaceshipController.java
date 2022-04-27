package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceshipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.service.spaceship.SpaceShipServiceImpl;
import com.mindera.school.spaceshiprent.util.LoggerHelper;
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
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SpaceshipController {

    public static final String PATH_CREATE_SPACESHIP = "/spaceships";
    public static final String PATH_GET_SPACESHIPS = "/spaceships";
    public static final String PATH_GET_SPACESHIP_BY_ID = "/spaceships/{id}";
    public static final String PATH_UPDATE_SPACESHIP = "/spaceships/{id}";

    private final SpaceShipServiceImpl spaceShipService;

    @PostMapping(PATH_CREATE_SPACESHIP)
    public ResponseEntity<SpaceShipDetailsDto> createSpaceship(@RequestBody @Valid CreateOrUpdateSpaceshipDto dto){
        log.info(LoggerHelper.POST_REQUEST, LoggerHelper.SPACESHIP, LocalDate.now());
        return ResponseEntity.ok(spaceShipService.createSpaceShip(dto));
    }

    @GetMapping(PATH_GET_SPACESHIPS)
    public ResponseEntity<List<SpaceShipDetailsDto>> getAllSpaceShips(){
        log.info(LoggerHelper.GET_ALL_REQUEST, LoggerHelper.SPACESHIP, LocalDate.now());
        return ResponseEntity.ok(spaceShipService.getAllSpaceShips());
    }

    @GetMapping(PATH_GET_SPACESHIP_BY_ID)
    public ResponseEntity<SpaceShipDetailsDto> getSpaceShipById(@PathVariable Long id) {
        log.info(LoggerHelper.GET_REQUEST, LoggerHelper.SPACESHIP, LocalDate.now());
        return ResponseEntity.ok(spaceShipService.getSpaceShipById(id));
    }

    @PutMapping(PATH_UPDATE_SPACESHIP)
    public ResponseEntity<SpaceShipDetailsDto> updateSpaceshipById(@PathVariable Long id,
                                                                   @RequestBody @Valid CreateOrUpdateSpaceshipDto dto){
        log.info(LoggerHelper.PUT_REQUEST, LoggerHelper.SPACESHIP, LocalDate.now());
        return ResponseEntity.ok(spaceShipService.updateSpaceShipById(id,dto));
    }

}
