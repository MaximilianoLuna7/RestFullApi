package com.maxiluna.studentmanagement.domain.usecases.attendance;

import com.maxiluna.studentmanagement.domain.models.Attendance;

import java.util.List;

public interface GetAttendancesByClassRecordUseCase {
    List<Attendance> execute(Long classRecordId);
}
