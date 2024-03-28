package com.maxiluna.studentmanagement.domain.usecases.subject;

import com.maxiluna.studentmanagement.domain.models.Subject;

import java.util.List;

public interface GetSubjectsByCourseUseCase {
    List<Subject> getSubjectByCourse(Long courseId);
}
