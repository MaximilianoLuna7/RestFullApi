package com.maxiluna.studentmanagement.domain.usecases.course;

import com.maxiluna.studentmanagement.domain.models.Course;

public interface CreateCourseUseCase {
    void createCourse(Course courseToCreate);
}
