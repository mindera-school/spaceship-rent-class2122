package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.service.rentService.RentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class RentController {

    private final RentService rentService;


    @PostMapping("/rent/")
    public ResponseEntity<RentDetailsDto> createRent(@RequestBody CreateOrUpdateRentDto dto) {
        return ResponseEntity.ok(rentService.createRent(dto));
    }

    @GetMapping("/rent/")
    public ResponseEntity<List<RentDetailsDto>> getAllRents() {
        return ResponseEntity.ok(rentService.getAllRents());
    }

    @GetMapping("/rent/{id}")
    public ResponseEntity<RentDetailsDto> getRentById(@PathVariable Long id){
        return ResponseEntity.ok(rentService.getRentById(id));
    }

    @GetMapping("/user/{id}/rent")
    public ResponseEntity<List<RentDetailsDto>> getRentByUserId(@PathVariable Long userid){
        return ResponseEntity.ok(rentService.getRentByUserId(userid));
    }

    @GetMapping("/spaceship/{id}/rent")
    public ResponseEntity<List<RentDetailsDto>> getRentBySpaceshipId(@PathVariable Long spaceshipId){
        return ResponseEntity.ok(rentService.getRentBySpaceShipId(spaceshipId));
    }

    @PutMapping("/rent/{id}")
    public ResponseEntity<RentDetailsDto> updateRentById(@PathVariable Long id, @RequestBody CreateOrUpdateRentDto dto) {
        return ResponseEntity.ok(rentService.updateRent(id,dto));
    }
}



