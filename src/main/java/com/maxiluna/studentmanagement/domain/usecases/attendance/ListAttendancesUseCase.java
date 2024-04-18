package com.maxiluna.studentmanagement.domain.usecases.attendance;

import com.maxiluna.studentmanagement.domain.models.Attendance;

import java.util.List;

public interface ListAttendancesUseCase {
    List<Attendance> execute();
}
