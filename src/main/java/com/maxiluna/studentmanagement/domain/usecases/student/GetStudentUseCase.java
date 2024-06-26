package com.maxiluna.studentmanagement.domain.usecases.student;

import com.maxiluna.studentmanagement.domain.models.Student;

public interface GetStudentUseCase {
    Student execute(Long studentId);
}
