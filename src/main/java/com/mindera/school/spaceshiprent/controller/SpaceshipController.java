package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceshipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceshipDetailsDto;
import com.mindera.school.spaceshiprent.service.spaceship.SpaceShipServiceImpl;
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

import static com.mindera.school.spaceshiprent.controller.Paths.PATH_GET_SPACESHIPS;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_GET_SPACESHIP_BY_ID;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_POST_SPACESHIP;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_UPDATE_SPACESHIP_BY_ID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SpaceshipController {

    private final SpaceShipServiceImpl spaceShipService;

    @PostMapping(PATH_POST_SPACESHIP)
    public ResponseEntity<SpaceshipDetailsDto> createSpaceship(@RequestBody @Valid CreateOrUpdateSpaceshipDto dto) {
        log.info(LoggerMessages.POST_REQUEST, LoggerMessages.SPACESHIP);
        return ResponseEntity.ok(spaceShipService.createSpaceShip(dto));
    }

    @GetMapping(PATH_GET_SPACESHIPS)
    public ResponseEntity<List<SpaceshipDetailsDto>> getAllSpaceShips() {
        log.info(LoggerMessages.GET_ALL_REQUEST, LoggerMessages.SPACESHIP);
        return ResponseEntity.ok(spaceShipService.getAllSpaceShips());
    }

    @GetMapping(PATH_GET_SPACESHIP_BY_ID)
    public ResponseEntity<SpaceshipDetailsDto> getSpaceShipById(@PathVariable Long id) {
        log.info(LoggerMessages.GET_REQUEST, LoggerMessages.SPACESHIP);
        return ResponseEntity.ok(spaceShipService.getSpaceShipById(id));
    }

    @PutMapping(PATH_UPDATE_SPACESHIP_BY_ID)
    public ResponseEntity<SpaceshipDetailsDto> updateSpaceshipById(
            @PathVariable Long id,
            @RequestBody @Valid CreateOrUpdateSpaceshipDto dto
    ) {
        log.info(LoggerMessages.PUT_REQUEST, LoggerMessages.SPACESHIP);
        return ResponseEntity.ok(spaceShipService.updateSpaceShipById(id, dto));
    }

}
