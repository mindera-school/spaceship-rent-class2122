package com.mindera.school.spaceshiprent.dto.spaceship;

import lombok.Data;

@Data
public class CreateOrUpdateSpaceShipDto {

    private String name;
    private String brand;
    private String model;
    private int registerNumber;
    private float priceDay;
}
