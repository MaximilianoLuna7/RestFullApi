package com.maxiluna.studentmanagement.infrastructure.persistence;

import com.maxiluna.studentmanagement.infrastructure.entities.GradeJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaGradeRepository extends JpaRepository<GradeJpa, Long> {
    List<GradeJpa> findBySubjectId(Long subjectId);
    List<GradeJpa> findByStudentId(Long studentId);
    List<GradeJpa> findByClassRecordId(Long classRecordId);
}
