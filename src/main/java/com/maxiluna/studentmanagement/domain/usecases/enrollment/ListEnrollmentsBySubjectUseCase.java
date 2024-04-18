package com.maxiluna.studentmanagement.domain.usecases.enrollment;

import com.maxiluna.studentmanagement.domain.models.Enrollment;

import java.util.List;

public interface ListEnrollmentsBySubjectUseCase {
    List<Enrollment> execute(Long subjectId);
}
