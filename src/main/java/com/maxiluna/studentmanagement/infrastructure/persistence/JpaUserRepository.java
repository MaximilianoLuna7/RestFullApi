package com.maxiluna.studentmanagement.infrastructure.persistence;

import com.maxiluna.studentmanagement.infrastructure.entities.UserJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<UserJpa, Long> {
    boolean existsByEmail(String email);

    Optional<UserJpa> findByEmail(String email);
}
