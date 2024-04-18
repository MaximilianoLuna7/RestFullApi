package com.maxiluna.studentmanagement.domain.usecases.student;

import com.maxiluna.studentmanagement.domain.models.StudentStatus;

public interface UpdateStudentStatusUseCase {
    void execute(Long studentId, Long subjectId, StudentStatus updatedStatus);
}
