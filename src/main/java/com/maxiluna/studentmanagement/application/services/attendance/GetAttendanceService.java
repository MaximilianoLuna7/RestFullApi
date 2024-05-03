package com.maxiluna.studentmanagement.application.services.attendance;

import com.maxiluna.studentmanagement.domain.exceptions.AttendanceNotFoundException;
import com.maxiluna.studentmanagement.domain.models.Attendance;
import com.maxiluna.studentmanagement.domain.usecases.attendance.GetAttendanceUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.AttendanceJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaAttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetAttendanceService implements GetAttendanceUseCase {
    @Autowired
    private JpaAttendanceRepository attendanceRepository;

    @Override
    public Attendance execute(Long attendanceId) {
        if (attendanceId <= 0) {
            throw new IllegalArgumentException("Invalid attendance ID: " + attendanceId);
        }

        AttendanceJpa attendanceJpa = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new AttendanceNotFoundException("Attendance not found with ID: " + attendanceId));

        return attendanceJpa.toAttendance();
    }
}
