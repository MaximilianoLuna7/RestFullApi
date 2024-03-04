package com.maxiluna.studentmanagement.application.services.course;

import com.maxiluna.studentmanagement.domain.models.Course;
import com.maxiluna.studentmanagement.infrastructure.entities.CourseJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaCourseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCourseServiceTest {

    @Mock
    private JpaCourseRepository courseRepository;

    @InjectMocks
    private CreateCourseService createCourseService;

    @Test
    @DisplayName("Create course - Successful")
    public void createCourse_Successful() {
        // Arrange
        String name = "Engineering";
        String institutionName = "University";
        Double years = 3.5;

        Course course = Course.builder()
                .name(name)
                .institutionName(institutionName)
                .durationInYears(years)
                .build();

        CourseJpa courseJpa = CourseJpa.fromCourse(course);

        // Act
        createCourseService.createCourse(course);

        // Verify
        verify(courseRepository, times(1)).save(courseJpa);
    }
}