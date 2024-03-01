package com.maxiluna.studentmanagement.infrastructure.persistence;

import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSubjectRepository extends JpaRepository<SubjectJpa, Long> {
}
