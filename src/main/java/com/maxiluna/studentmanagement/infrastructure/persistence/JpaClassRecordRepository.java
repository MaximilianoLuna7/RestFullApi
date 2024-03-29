package com.maxiluna.studentmanagement.infrastructure.persistence;

import com.maxiluna.studentmanagement.infrastructure.entities.ClassRecordJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaClassRecordRepository extends JpaRepository<ClassRecordJpa, Long> {
    List<ClassRecordJpa> findBySubjectId(Long subjectId);
}
