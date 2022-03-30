package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceShipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.service.spaceShipService.SpaceShipServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/spaceships")
public class SpaceShipController {

    private final SpaceShipServiceImpl spaceShipService;

    @PostMapping()
    public ResponseEntity<SpaceShipDetailsDto> createSpaceship(@RequestBody CreateOrUpdateSpaceShipDto dto){
        log.info("Request to create spaceship");
        return ResponseEntity.ok(spaceShipService.createSpaceShip(dto));
    }

    @GetMapping()
    public ResponseEntity<List<SpaceShipDetailsDto>> getAllSpaceShips(){
        log.info("Request to get all spaceships");
        return ResponseEntity.ok(spaceShipService.getAllSpaceShips());
    }

    @GetMapping("{id}")
    public ResponseEntity<SpaceShipDetailsDto> getSpaceShipById(@PathVariable Long id){
        log.info("Request to get spaceship by id {}", id);
        return ResponseEntity.ok(spaceShipService.getSpaceShipById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<SpaceShipDetailsDto> updateSpaceshipById(@PathVariable Long id, @RequestBody CreateOrUpdateSpaceShipDto dto){
        log.info("Request to update spaceship by id {}", id);
        return ResponseEntity.ok(spaceShipService.updateSpaceShipById(id,dto));
    }

}
