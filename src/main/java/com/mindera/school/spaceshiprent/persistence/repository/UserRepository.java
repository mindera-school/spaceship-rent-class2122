package com.mindera.school.spaceshiprent.persistence.repository;

import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
