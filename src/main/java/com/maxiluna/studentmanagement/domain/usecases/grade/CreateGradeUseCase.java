package com.maxiluna.studentmanagement.domain.usecases.grade;

import com.maxiluna.studentmanagement.domain.models.Grade;

public interface CreateGradeUseCase {
    void execute(Grade gradeToCreate, Long studentId, Long classRecordId);
}
