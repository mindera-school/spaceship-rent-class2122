package com.mindera.school.spaceshiprent.dto.spaceship;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class CreateOrUpdateSpaceshipDto {

    @NotBlank
    private String name;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotNull
    private int registerNumber;

    @Positive
    private float priceDay;
}
