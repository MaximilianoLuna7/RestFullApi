package com.maxiluna.studentmanagement.domain.usecases.course;

import com.maxiluna.studentmanagement.domain.models.Course;

public interface GetCourseDataUseCase {
    Course execute(Long courseId);
}
