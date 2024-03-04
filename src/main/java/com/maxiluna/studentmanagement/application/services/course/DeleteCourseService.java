package com.maxiluna.studentmanagement.application.services.course;

import com.maxiluna.studentmanagement.domain.exceptions.CourseNotFoundException;
import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.usecases.course.DeleteCourseUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.CourseJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteCourseService implements DeleteCourseUseCase {

    @Autowired
    private JpaCourseRepository courseRepository;

    @Override
    @Transactional
    public void deleteCourse(Long courseId) {
        if (courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("Invalid course ID: " + courseId);
        }

        try {
            CourseJpa courseJpa = courseRepository.findById(courseId)
                    .orElseThrow(() -> new  CourseNotFoundException("Course not found with ID: " + courseId));

            courseRepository.delete(courseJpa);
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error deleting course information: " + ex.getMessage());
        }
    }
}
