package com.maxiluna.studentmanagement.domain.usecases.student;

import com.maxiluna.studentmanagement.domain.models.Student;

public interface UpdateStudentUseCase {
    void execute(Long studentId, Student updatedStudent);
}
