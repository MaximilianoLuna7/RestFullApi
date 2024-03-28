package com.maxiluna.studentmanagement.domain.usecases.subject;

import com.maxiluna.studentmanagement.domain.models.Subject;

public interface CreateSubjectUseCase {
    void createSubject(Subject subjectToCreate, Long courseId, Long teacherId);
}
