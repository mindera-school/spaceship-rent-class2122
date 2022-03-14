package com.mindera.school.spaceshiprent.dto.rent;

import lombok.Data;
import java.time.LocalDate;


@Data
public class CreateOrUpdateRentDto {

    private Long customerId;
    private Long spaceshipId;
    private LocalDate expectedPickupDate;
    private LocalDate expectedReturnDate;
    private float pricePerDay;
    private float discount;
}
