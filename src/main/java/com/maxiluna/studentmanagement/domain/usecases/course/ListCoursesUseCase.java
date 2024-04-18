package com.maxiluna.studentmanagement.domain.usecases.course;

import com.maxiluna.studentmanagement.domain.models.Course;

import java.util.List;

public interface ListCoursesUseCase {
    List<Course> execute();
}
