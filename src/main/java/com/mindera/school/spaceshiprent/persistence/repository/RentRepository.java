package com.mindera.school.spaceshiprent.persistence.repository;

import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RentRepository extends JpaRepository<RentEntity, Long> {

    @Query(value = "select case when exists (select * from rents r \n" +
            "where spaceship_id = :spaceshipId and\n" +
            "expected_pickup_date >= :pickUpDate and expected_pickup_date <= :returnDate\n" +
            "or expected_return_date >= :pickUpDate and expected_return_date <= :returnDate)\n" +
            "then 'false' else 'true' end", nativeQuery = true)
    boolean checkRentAvailability(@Param(value = "pickUpDate") LocalDate expectedPickupDate, @Param(value = "returnDate") LocalDate expectedReturnDate, @Param(value = "spaceshipId") Long spaceshipID);
}
