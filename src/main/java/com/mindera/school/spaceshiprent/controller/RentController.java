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


    @PostMapping("/rents/")
    public ResponseEntity<RentDetailsDto> createRent(@RequestBody CreateOrUpdateRentDto dto) {
        return ResponseEntity.ok(rentService.createRent(dto));
    }

    @GetMapping("/rents/")
    public ResponseEntity<List<RentDetailsDto>> getAllRents() {
        return ResponseEntity.ok(rentService.getAllRents());
    }

    @GetMapping("/rents/{id}")
    public ResponseEntity<RentDetailsDto> getRentById(@PathVariable Long id){
        return ResponseEntity.ok(rentService.getRentById(id));
    }

    @GetMapping("/users/{id}/rents")
    public ResponseEntity<List<RentDetailsDto>> getRentByUserId(@PathVariable("id")  Long userid){
        return ResponseEntity.ok(rentService.getRentByUserId(userid));
    }

    @GetMapping("/spaceships/{id}/rents")
    public ResponseEntity<List<RentDetailsDto>> getRentBySpaceshipId(@PathVariable("id") Long spaceshipId){
        return ResponseEntity.ok(rentService.getRentBySpaceShipId(spaceshipId));
    }

    @PutMapping("/rents/{id}")
    public ResponseEntity<RentDetailsDto> updateRentById(@PathVariable Long id, @RequestBody CreateOrUpdateRentDto dto) {
        return ResponseEntity.ok(rentService.updateRent(id,dto));
    }
}



