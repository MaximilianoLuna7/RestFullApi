package com.maxiluna.studentmanagement.domain.usecases.classRecord;

import com.maxiluna.studentmanagement.domain.models.ClassRecord;

public interface CreateClassRecordUseCase {
    void execute(ClassRecord classRecordToCreate, Long subjectId);
}
