package com.mindera.school.spaceshiprent.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rents")
public class RentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spaceship_id")
    private SpaceShipEntity spaceShipEntity;

    @Column(nullable = false)
    private LocalDate expectedPickupDate;

    @Column(nullable = false)
    private LocalDate expectedReturnDate;

    @Column(nullable = true)
    private LocalDate pickupDate;

    @Column(nullable = true)
    private LocalDate returnDate;

    @Column(nullable = false)
    private float pricePerDay;

    @Column(nullable = false)
    private int discount;
}
