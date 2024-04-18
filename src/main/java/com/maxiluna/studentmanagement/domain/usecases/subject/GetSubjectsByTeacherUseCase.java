package com.maxiluna.studentmanagement.domain.usecases.subject;

import com.maxiluna.studentmanagement.domain.models.Subject;

import java.util.List;

public interface GetSubjectsByTeacherUseCase {
    List<Subject> execute(Long teacherId);
}
