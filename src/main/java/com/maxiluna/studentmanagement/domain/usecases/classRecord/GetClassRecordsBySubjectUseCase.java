package com.maxiluna.studentmanagement.domain.usecases.classRecord;

import com.maxiluna.studentmanagement.domain.models.ClassRecord;

import java.util.List;

public interface GetClassRecordsBySubjectUseCase {
    List<ClassRecord> execute(Long subjectId);
}
