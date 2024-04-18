package com.maxiluna.studentmanagement.domain.usecases.attendance;

import com.maxiluna.studentmanagement.domain.models.Attendance;

public interface GetAttendanceUseCase {
    Attendance execute(Long attendanceId);
}
