package com.maxiluna.studentmanagement.domain.usecases.grade;

import com.maxiluna.studentmanagement.domain.models.Grade;

import java.util.List;

public interface GetGradesByStudentAndSubjectUseCase {
    List<Grade> execute(Long studentId, Long subjectId);
}
