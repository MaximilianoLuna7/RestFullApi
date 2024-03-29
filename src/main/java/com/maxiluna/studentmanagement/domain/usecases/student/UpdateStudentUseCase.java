package com.maxiluna.studentmanagement.domain.usecases.student;

import com.maxiluna.studentmanagement.domain.models.Student;

public interface UpdateStudentUseCase {
    void updateStudent(Long studentId, Student updatedStudent);
}
