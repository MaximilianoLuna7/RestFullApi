package com.maxiluna.studentmanagement.infrastructure.persistence;

import com.maxiluna.studentmanagement.infrastructure.entities.EnrollmentJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaEnrollmentRepository extends JpaRepository<EnrollmentJpa, Long> {
    List<EnrollmentJpa> findByStudentId(Long studentId);
    List<EnrollmentJpa> findBySubjectId(Long subjectId);
    Optional<EnrollmentJpa> findByStudentIdAndSubjectId(Long studentId, Long subjectId);
}
