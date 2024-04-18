package com.maxiluna.studentmanagement.domain.usecases.student;

import com.maxiluna.studentmanagement.domain.models.Student;

import java.util.List;

public interface GetStudentsBySubjectUseCase {
    List<Student> execute(Long subjectId);
}
