package com.maxiluna.studentmanagement.domain.usecases.student;

import com.maxiluna.studentmanagement.domain.models.Student;
import com.maxiluna.studentmanagement.domain.models.StudentStatus;

public interface CreateStudentUseCase {
    void execute(Student studentToCreate, Long subjectId, StudentStatus status);
}

