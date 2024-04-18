package com.maxiluna.studentmanagement.application.services.course;

import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.models.Course;
import com.maxiluna.studentmanagement.domain.models.Subject;
import com.maxiluna.studentmanagement.domain.usecases.course.ListCoursesUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.CourseJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaCourseRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.PrimitiveIterator;
import java.util.stream.Collectors;

@Service
public class ListCoursesService implements ListCoursesUseCase {

    @Autowired
    private JpaCourseRepository courseRepository;

    @Autowired
    private JpaSubjectRepository subjectRepository;

    @Override
    public List<Course> execute() {
        try {
            List<CourseJpa> courseJpaList = courseRepository.findAll();

            return courseJpaList.stream()
                    .map(CourseJpa::toCourse)
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error listing courses: " + ex.getMessage());
        }
    }
}
