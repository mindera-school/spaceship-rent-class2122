package com.mindera.school.spaceshiprent.persistence.entity;

import com.mindera.school.spaceshiprent.enumerator.UserType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_id_generator"
    )
    @SequenceGenerator(
            name = "users_id_generator",
            allocationSize = 1,
            sequenceName = "users_id_generator"
    )
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    @Column(nullable = false, unique = true)
    private Long ssn;

    @Column(nullable = false)
    private String planet;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<RentEntity> rents;
}
