package com.mindera.school.spaceshiprent.dto.rent;

import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
public class CreateOrUpdateRentDto {

    @NotNull
    private Long customerId;

    @NotNull
    private Long spaceshipId;

    @NotNull
    private LocalDate expectedPickupDate;

    @FutureOrPresent
    private LocalDate expectedReturnDate;

    private float discount;
}
