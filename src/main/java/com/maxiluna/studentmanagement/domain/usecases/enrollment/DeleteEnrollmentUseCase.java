package com.maxiluna.studentmanagement.domain.usecases.enrollment;

public interface DeleteEnrollmentUseCase {
    void execute(Long studentId, Long subjectId);
}
