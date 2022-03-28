package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceshipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.service.spaceship.SpaceShipServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpaceshipController {

    private final SpaceShipServiceImpl spaceShipService;

    @PostMapping("/spaceships")
    public ResponseEntity<SpaceShipDetailsDto> createSpaceship(@RequestBody CreateOrUpdateSpaceshipDto dto){
        return ResponseEntity.ok(spaceShipService.createSpaceShip(dto));
    }

    @GetMapping("/spaceships")
    public ResponseEntity<List<SpaceShipDetailsDto>> getAllSpaceShips(){
        return ResponseEntity.ok(spaceShipService.getAllSpaceShips());
    }

    @GetMapping("/spaceships/{id}")
    public ResponseEntity<SpaceShipDetailsDto> getSpaceShipById(@PathVariable Long id){
        return ResponseEntity.ok(spaceShipService.getSpaceShipById(id));
    }

    @PutMapping("/spaceships/{id}")
    public ResponseEntity<SpaceShipDetailsDto> updateSpaceshipById(@PathVariable Long id, @RequestBody CreateOrUpdateSpaceshipDto dto){
        return ResponseEntity.ok(spaceShipService.updateSpaceShipById(id,dto));
    }

}
