package com.maxiluna.studentmanagement.application.services.course;

import com.maxiluna.studentmanagement.domain.exceptions.CourseNotFoundException;
import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.models.Course;
import com.maxiluna.studentmanagement.domain.usecases.course.UpdateCourseDataUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.CourseJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateCourseDataService implements UpdateCourseDataUseCase {

    @Autowired
    private JpaCourseRepository courseRepository;

    @Override
    @Transactional
    public void updateCourse(Long courseId, Course updatedCourse) {
        if (courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("Invalid course ID: " + courseId);
        }

        try {
            CourseJpa courseJpa = courseRepository.findById(courseId)
                    .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseId));

            courseJpa.setName(updatedCourse.getName());
            courseJpa.setInstitutionName(updatedCourse.getInstitutionName());
            courseJpa.setDurationInYears(updatedCourse.getDurationInYears());

            courseRepository.save(courseJpa);
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error updating course data: " + ex.getMessage());
        }
    }
}
