package com.mindera.school.spaceshiprent.persistence.repository;

import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentRepository extends JpaRepository<RentEntity, Long> {
}
