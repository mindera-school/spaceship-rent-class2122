package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceShipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.service.spaceShipService.SpaceShipServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spaceships")
public class SpaceShipController {

    private final SpaceShipServiceImpl spaceShipService;
    public static final Logger LOGGER = LoggerFactory.getLogger(SpaceShipController.class);

    @PostMapping()
    public ResponseEntity<SpaceShipDetailsDto> createSpaceship(@RequestBody CreateOrUpdateSpaceShipDto dto){
        LOGGER.info("created Spaceship {}",dto);
        return ResponseEntity.ok(spaceShipService.createSpaceShip(dto));
    }

    @GetMapping()
    public ResponseEntity<List<SpaceShipDetailsDto>> getAllSpaceShips(){
        return ResponseEntity.ok(spaceShipService.getAllSpaceShips());
    }

    @GetMapping("{id}")
    public ResponseEntity<SpaceShipDetailsDto> getSpaceShipById(@PathVariable Long id){
        LOGGER.info("requested Spaceship {}",id);
        return ResponseEntity.ok(spaceShipService.getSpaceShipById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<SpaceShipDetailsDto> updateSpaceshipById(@PathVariable Long id, @RequestBody CreateOrUpdateSpaceShipDto dto){
        LOGGER.info("updated Spaceship {}",id);
        return ResponseEntity.ok(spaceShipService.updateSpaceShipById(id,dto));
    }

}
