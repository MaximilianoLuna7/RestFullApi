package com.maxiluna.studentmanagement.domain.usecases.classRecord;

import com.maxiluna.studentmanagement.domain.models.ClassRecord;

public interface UpdateClassRecordUseCase {
    void execute(Long classRecordId, ClassRecord updatedClassRecord);
}
