package com.mindera.school.spaceshiprent.dto.spaceship;

import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

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
