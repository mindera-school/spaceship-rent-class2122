package com.mindera.school.spaceshiprent.dto.spaceship;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpaceShipDetailsDto {

    private Long id;
    private String name;
    private String brand;
    private String model;
    private int registerNumber;
    private float priceDay;
}
