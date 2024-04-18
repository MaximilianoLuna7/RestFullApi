package com.maxiluna.studentmanagement.domain.usecases.enrollment;

import com.maxiluna.studentmanagement.domain.models.Enrollment;
import com.maxiluna.studentmanagement.domain.models.StudentStatus;

public interface UpdateEnrollmentStatusUseCase {
    void execute(Long studentId, Long subjectId, StudentStatus newStatus);
}
