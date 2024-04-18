package com.maxiluna.studentmanagement.domain.usecases.subject;

import com.maxiluna.studentmanagement.domain.models.Subject;

public interface GetSubjectUseCase {
    Subject execute(Long subjectId);
}
