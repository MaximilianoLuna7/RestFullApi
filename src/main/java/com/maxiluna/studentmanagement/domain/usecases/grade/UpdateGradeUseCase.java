package com.maxiluna.studentmanagement.domain.usecases.grade;

import com.maxiluna.studentmanagement.domain.models.Grade;

public interface UpdateGradeUseCase {
    void execute(Long gradeId, Grade updatedGrade);
}
