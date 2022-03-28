package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceShipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.service.spaceShipService.SpaceShipServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class SpaceShipController {

    private final SpaceShipServiceImpl spaceShipService;

    @PostMapping("/spaceships/")
    public ResponseEntity<SpaceShipDetailsDto> createSpaceship(@RequestBody CreateOrUpdateSpaceShipDto dto){
        return ResponseEntity.ok(spaceShipService.createSpaceShip(dto));
    }

    @GetMapping("/spaceships/")
    public ResponseEntity<List<SpaceShipDetailsDto>> getAllSpaceShips(){
        return ResponseEntity.ok(spaceShipService.getAllSpaceShips());
    }

    @GetMapping("/spaceships/{id}")
    public ResponseEntity<SpaceShipDetailsDto> getSpaceShipById(@PathVariable Long id){
        return ResponseEntity.ok(spaceShipService.getSpaceShipById(id));
    }

    @PutMapping("/spaceships/{id}")
    public ResponseEntity<SpaceShipDetailsDto> updateSpaceshipById(@PathVariable Long id, @RequestBody CreateOrUpdateSpaceShipDto dto){
        return ResponseEntity.ok(spaceShipService.updateSpaceShipById(id,dto));
    }

}
