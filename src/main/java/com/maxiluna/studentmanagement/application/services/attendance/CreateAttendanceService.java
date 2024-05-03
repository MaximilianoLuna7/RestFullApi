package com.maxiluna.studentmanagement.application.services.attendance;

import com.maxiluna.studentmanagement.domain.exceptions.ClassRecordNotFoundException;
import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.exceptions.StudentNotFoundException;
import com.maxiluna.studentmanagement.domain.models.AttendanceStatus;
import com.maxiluna.studentmanagement.domain.usecases.attendance.CreateAttendanceUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.AttendanceJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.ClassRecordJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.StudentJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaAttendanceRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaClassRecordRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class CreateAttendanceService implements CreateAttendanceUseCase {
    @Autowired
    private JpaAttendanceRepository attendanceRepository;

    @Autowired
    private JpaStudentRepository studentRepository;

    @Autowired
    private JpaClassRecordRepository classRecordRepository;

    @Override
    @Transactional
    public void execute(Long studentId, Long classRecordId, AttendanceStatus status) {
        try {
            StudentJpa studentJpa = studentRepository.findById(studentId)
                    .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));
            ClassRecordJpa classRecordJpa = classRecordRepository.findById(classRecordId)
                    .orElseThrow(() -> new ClassRecordNotFoundException("Class record not found with ID: " + classRecordId));

            AttendanceJpa attendanceJpa = AttendanceJpa.builder()
                    .dateRecord(classRecordJpa.getDate())
                    .student(studentJpa)
                    .classRecord(classRecordJpa)
                    .attendanceStatus(status.name())
                    .build();

            attendanceRepository.save(attendanceJpa);
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error creating attendance: " + ex.getMessage());
        }
    }
}
