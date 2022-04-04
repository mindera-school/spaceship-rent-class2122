package com.mindera.school.spaceshiprent.persistence.entity;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rents_id_generator"
    )
    @SequenceGenerator(
            name = "rents_id_generator",
            allocationSize = 1,
            sequenceName = "rents_id_generator"
    )
    private Long id;

    @NotNull(message = "User cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spaceship_id")
    private SpaceShipEntity spaceShipEntity;

    @NotNull(message = "expectedPickupDate cannot be null")
    @Column(nullable = false)
    private LocalDate expectedPickupDate;

    @NotNull(message = "expectedReturnDate cannot be null")
    @Column(nullable = false)
    private LocalDate expectedReturnDate;

    @NotNull(message = "PickupDate cannot be null")
    @Column(nullable = true)
    private LocalDate pickupDate;

    @Column(nullable = true)
    private LocalDate returnDate;

    @Column(nullable = false)
    private float pricePerDay;

    @Column(nullable = false)
    private float discount;
}
