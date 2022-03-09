package com.mindera.school.spaceshiprent.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
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
    private UserEntity accountEntity;

    @ManyToOne()
    @JoinColumn(name = "spaceship_id")
    private SpaceshipEntity vehicleEntity;

    @Column(nullable = false)
    private Date expectedpickdate;

    @Column(nullable = false)
    private Date expectedreturndate;

    @Column(nullable = false)
    private Date pickdate;

    @Column(nullable = false)
    private Date returndate;

    @Column(nullable = false)
    private float priceperday;

    @Column(nullable = false)
    private int discount;
}
