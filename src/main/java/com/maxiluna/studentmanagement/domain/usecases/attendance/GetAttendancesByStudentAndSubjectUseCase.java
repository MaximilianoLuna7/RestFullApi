package com.maxiluna.studentmanagement.domain.usecases.attendance;

import com.maxiluna.studentmanagement.domain.models.Attendance;

import java.util.List;

public interface GetAttendancesByStudentAndSubjectUseCase {
    List<Attendance> execute(Long studentId, Long subjectId);
}
