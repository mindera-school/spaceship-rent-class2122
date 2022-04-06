package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceshipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.service.spaceship.SpaceShipServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SpaceShipController {

    private final SpaceShipServiceImpl spaceShipService;

    @PostMapping("/spaceships")
    public ResponseEntity<SpaceShipDetailsDto> createSpaceship(@RequestBody CreateOrUpdateSpaceshipDto dto){
        log.info("created Spaceship {}",dto);
        return ResponseEntity.ok(spaceShipService.createSpaceShip(dto));
    }

    @GetMapping("/spaceships")
    public ResponseEntity<List<SpaceShipDetailsDto>> getAllSpaceShips(){
        return ResponseEntity.ok(spaceShipService.getAllSpaceShips());
    }

    @GetMapping("/spaceships/{id}")
    public ResponseEntity<SpaceShipDetailsDto> getSpaceShipById(@PathVariable Long id){
        log.info("requested Spaceship {}",id);
        return ResponseEntity.ok(spaceShipService.getSpaceShipById(id));
    }

    @PutMapping("/spaceships/{id}")
    public ResponseEntity<SpaceShipDetailsDto> updateSpaceshipById(@PathVariable Long id, @RequestBody CreateOrUpdateSpaceshipDto dto){
        log.info("updated Spaceship {}",id);
        return ResponseEntity.ok(spaceShipService.updateSpaceShipById(id,dto));
    }

}
