package com.maxiluna.studentmanagement.domain.usecases.attendance;

import com.maxiluna.studentmanagement.domain.models.AttendanceStatus;

public interface CreateAttendanceUseCase {
    void execute(Long studentId, Long classRecordId, AttendanceStatus status);
}
