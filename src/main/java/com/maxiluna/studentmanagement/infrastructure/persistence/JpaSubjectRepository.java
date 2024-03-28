package com.maxiluna.studentmanagement.infrastructure.persistence;

import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaSubjectRepository extends JpaRepository<SubjectJpa, Long> {
    List<SubjectJpa> findByTeacherId(Long teacherId);
    List<SubjectJpa> findByCourseId(Long courseId);
}
