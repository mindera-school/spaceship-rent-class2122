package com.mindera.school.spaceshiprent.controller;

import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.service.rent.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RentController {

    private final RentService rentService;

    @PostMapping("/rents")
    public ResponseEntity<RentDetailsDto> createRent(@RequestBody CreateOrUpdateRentDto dto) {
        return ResponseEntity.ok(rentService.createRent(dto));
    }

    @GetMapping("/rents")
    public ResponseEntity<List<RentDetailsDto>> getAllRents() {
        return ResponseEntity.ok(rentService.getAllRents());
    }

    @GetMapping("/rents/{id}")
    public ResponseEntity<RentDetailsDto> getRentById(@PathVariable Long id){
        return ResponseEntity.ok(rentService.getRentById(id));
    }

    @GetMapping("/customers/{customerId}/rents")
    public ResponseEntity<List<RentDetailsDto>> getRentByCostumerId(@PathVariable Long costumerId){
        return ResponseEntity.ok(rentService.getRentByCustomerId(costumerId));
    }

    @GetMapping("/spaceships/{spaceshipId}/rents")
    public ResponseEntity<List<RentDetailsDto>> getRentBySpaceshipId(@PathVariable Long spaceshipId){
        return ResponseEntity.ok(rentService.getRentBySpaceShipId(spaceshipId));
    }

    @PutMapping("/rents/{id}")
    public ResponseEntity<RentDetailsDto> updateRentById(@PathVariable Long id, @RequestBody CreateOrUpdateRentDto dto) {
        return ResponseEntity.ok(rentService.updateRent(id,dto));
    }
}



