package com.maxiluna.studentmanagement.domain.usecases.course;

import com.maxiluna.studentmanagement.domain.models.Course;

public interface UpdateCourseDataUseCase {
    void execute(Long courseId, Course updatedCourse);
}
