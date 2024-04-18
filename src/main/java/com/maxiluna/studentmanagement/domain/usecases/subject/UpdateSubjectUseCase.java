package com.maxiluna.studentmanagement.domain.usecases.subject;

import com.maxiluna.studentmanagement.domain.models.Subject;

public interface UpdateSubjectUseCase {
    void execute(Long subjectId, Subject updatedSubject);
}
