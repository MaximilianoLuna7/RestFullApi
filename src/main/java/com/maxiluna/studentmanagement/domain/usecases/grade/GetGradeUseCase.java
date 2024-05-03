package com.maxiluna.studentmanagement.domain.usecases.grade;

import com.maxiluna.studentmanagement.domain.models.Grade;

public interface GetGradeUseCase {
    Grade execute(Long gradeId);
}
