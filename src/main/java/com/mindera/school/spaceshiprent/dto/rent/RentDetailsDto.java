package com.mindera.school.spaceshiprent.dto.rent;


import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RentDetailsDto {

    private Long id;
    private UserDetailsDto userDetailsDto;
    private SpaceShipDetailsDto spaceshipDetailsDto;
    private LocalDate expectedPickupDate;
    private LocalDate expectedReturnDate;
    private LocalDate pickDate;
    private LocalDate returnDate;
    private float pricePerDay;
    private float discount;
}
