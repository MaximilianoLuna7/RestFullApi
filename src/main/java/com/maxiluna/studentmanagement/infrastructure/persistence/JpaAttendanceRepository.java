package com.maxiluna.studentmanagement.infrastructure.persistence;

import com.maxiluna.studentmanagement.infrastructure.entities.AttendanceJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaAttendanceRepository extends JpaRepository<AttendanceJpa, Long> {
    List<AttendanceJpa> findByStudentId(Long studentId);
    List<AttendanceJpa> findByClassRecordId(Long classRecordId);
}
