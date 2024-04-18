package com.maxiluna.studentmanagement.domain.usecases.attendance;

import com.maxiluna.studentmanagement.domain.models.AttendanceStatus;

public interface UpdateAttendanceStatusUseCase {
    void execute(Long attendanceId, AttendanceStatus updatedStatus);
}
