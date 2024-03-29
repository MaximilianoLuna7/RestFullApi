package com.maxiluna.studentmanagement.domain.usecases.student;

import com.maxiluna.studentmanagement.domain.models.Student;

public interface CreateStudentUseCase {
    void createStudent(Student studentToCreate);
}
