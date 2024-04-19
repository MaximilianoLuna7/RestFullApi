package com.maxiluna.studentmanagement.domain.usecases.classRecord;

import com.maxiluna.studentmanagement.domain.models.ClassRecord;

public interface GetClassRecordUseCase {
    ClassRecord execute(Long classRecordId);
}
