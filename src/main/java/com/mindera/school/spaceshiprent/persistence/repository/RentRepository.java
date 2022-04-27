package com.mindera.school.spaceshiprent.persistence.repository;

import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentRepository extends JpaRepository<RentEntity, Long> {

    List<RentEntity> findByUserId(Long userId);

    List<RentEntity> findBySpaceshipId(Long spaceShipId);

    Optional<RentEntity> findByIdAndUserId(Long id, Long userId);
}
