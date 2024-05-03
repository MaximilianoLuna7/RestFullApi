package com.maxiluna.studentmanagement.application.services.attendance;

import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.models.Attendance;
import com.maxiluna.studentmanagement.domain.usecases.attendance.GetAttendancesByClassRecordUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.AttendanceJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaAttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAttendancesByClassRecordService implements GetAttendancesByClassRecordUseCase {
    @Autowired
    private JpaAttendanceRepository attendanceRepository;

    @Override
    public List<Attendance> execute(Long classRecordId) {
        if (classRecordId <= 0) {
            throw new IllegalArgumentException("Invalid clas record ID: " + classRecordId);
        }

        try {
            List<AttendanceJpa> attendanceJpaList = attendanceRepository.findByClassRecordId(classRecordId);

            return attendanceJpaList.stream()
                    .map(AttendanceJpa::toAttendance)
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error listing attendances: " + ex.getMessage());
        }
    }
}
