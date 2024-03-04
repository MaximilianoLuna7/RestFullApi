package com.maxiluna.studentmanagement.application.services.course;

import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.models.Course;
import com.maxiluna.studentmanagement.domain.usecases.course.CreateCourseUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.CourseJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateCourseService implements CreateCourseUseCase {

    @Autowired
    private JpaCourseRepository courseRepository;

    @Override
    @Transactional
    public void createCourse(Course courseToCreate) {
        try {
            CourseJpa courseJpa = CourseJpa.fromCourse(courseToCreate);

            courseRepository.save(courseJpa);
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error creating course: " + ex.getMessage());
        }
    }
}
