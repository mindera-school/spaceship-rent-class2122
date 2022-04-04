package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.service.rentService.RentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rents")
public class RentController {

    private final RentService rentService;

    @PostMapping
    public ResponseEntity<RentDetailsDto> createRent(@RequestBody CreateOrUpdateRentDto dto) {

        return ResponseEntity.ok(rentService.createRent(dto));
    }

    @GetMapping
    public ResponseEntity<List<RentDetailsDto>> getAllRents() {
        log.info("Request received");
        return ResponseEntity.ok(rentService.getAllRents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentDetailsDto> getRentById(@PathVariable Long id){
        log.info("Request received, {}", id);
        return ResponseEntity.ok(rentService.getRentById(id));
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<List<RentDetailsDto>> getRentByCostumerId(@PathVariable Long costumerId){
        log.info("Request received, {}", costumerId);
        return ResponseEntity.ok(rentService.getRentByCustomerId(costumerId));
    }

    @GetMapping("/spaceships/{spaceshipId}")
    public ResponseEntity<List<RentDetailsDto>> getRentBySpaceshipId(@PathVariable Long spaceshipId){
        log.info("Request received");
        return ResponseEntity.ok(rentService.getRentBySpaceShipId(spaceshipId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentDetailsDto> updateRentById(@PathVariable Long id, @RequestBody CreateOrUpdateRentDto dto) {
        log.info("Request received, {}", dto);
        return ResponseEntity.ok(rentService.updateRent(id,dto));
    }
}



