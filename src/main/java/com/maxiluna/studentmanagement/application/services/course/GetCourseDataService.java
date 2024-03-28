package com.maxiluna.studentmanagement.application.services.course;

import com.maxiluna.studentmanagement.domain.exceptions.CourseNotFoundException;
import com.maxiluna.studentmanagement.domain.models.Course;
import com.maxiluna.studentmanagement.domain.models.Subject;
import com.maxiluna.studentmanagement.domain.usecases.course.GetCourseDataUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.CourseJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaCourseRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetCourseDataService implements GetCourseDataUseCase {

    @Autowired
    private JpaCourseRepository courseRepository;

    @Override
    public Course getCourseData(Long courseId) {
        if (courseId <= 0) {
            throw new IllegalArgumentException("Invalid course ID: " + courseId);
        }

        CourseJpa courseJpa = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseId));

        return courseJpa.toCourse();
    }
}
