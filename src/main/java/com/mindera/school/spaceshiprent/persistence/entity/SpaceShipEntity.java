package com.mindera.school.spaceshiprent.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "spaceships")

public class SpaceShipEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "spaceship_id_generator"
    )
    @SequenceGenerator(
            name = "spaceship_id_generator",
            allocationSize = 1,
            sequenceName = "spaceship_id_generator"
    )
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false, unique = true)
    private int registerNumber;

    @Column(nullable = false)
    private float priceDay;

    @OneToMany(mappedBy="spaceShipEntity", cascade = CascadeType.ALL)
    private List<RentEntity> rentEntity;
}
