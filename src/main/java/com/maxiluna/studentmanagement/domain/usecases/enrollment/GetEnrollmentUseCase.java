package com.maxiluna.studentmanagement.domain.usecases.enrollment;

import com.maxiluna.studentmanagement.domain.models.Enrollment;

public interface GetEnrollmentUseCase {
    Enrollment execute(Long studentId, Long subjectId);
}
