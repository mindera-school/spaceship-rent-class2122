package com.mindera.school.spaceshiprent.persistence.repository;

import com.mindera.school.spaceshiprent.persistence.entity.SpaceShipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceShipRepository extends JpaRepository<SpaceShipEntity,Long> {
}
