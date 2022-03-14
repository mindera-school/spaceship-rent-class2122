package com.mindera.school.spaceshiprent.dto.rent;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RentDetailsDto {

    private Long id;
    private Long customerId;
    private Long spaceshipId;
    private LocalDate expectedPickupDate;
    private LocalDate expectedReturnDate;
    private LocalDate pickDate;
    private LocalDate returnDate;
    private float pricePerDay;
    private float discount;
}
