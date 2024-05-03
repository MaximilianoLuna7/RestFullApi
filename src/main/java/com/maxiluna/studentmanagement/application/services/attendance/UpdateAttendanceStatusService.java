package com.maxiluna.studentmanagement.application.services.attendance;

import com.maxiluna.studentmanagement.domain.exceptions.AttendanceNotFoundException;
import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.models.AttendanceStatus;
import com.maxiluna.studentmanagement.domain.usecases.attendance.UpdateAttendanceStatusUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.AttendanceJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaAttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateAttendanceStatusService implements UpdateAttendanceStatusUseCase {
    @Autowired
    private JpaAttendanceRepository attendanceRepository;

    @Override
    @Transactional
    public void execute(Long attendanceId, AttendanceStatus updatedStatus) {
        if (attendanceId <= 0) {
            throw new IllegalArgumentException("Invalid attendance ID: " + attendanceId);
        }

        try {
            AttendanceJpa attendanceJpa = attendanceRepository.findById(attendanceId)
                    .orElseThrow(() -> new AttendanceNotFoundException("Attendance not found with ID: " + attendanceId));

            attendanceJpa.setAttendanceStatus(updatedStatus.name());
            attendanceRepository.save(attendanceJpa);
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error updating attendance: " + ex.getMessage());
        }
    }
}
