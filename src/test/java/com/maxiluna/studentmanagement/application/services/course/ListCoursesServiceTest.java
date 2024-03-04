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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListCoursesServiceTest {

    @Mock
    private JpaCourseRepository courseRepository;

    @InjectMocks
    private ListCoursesService listCoursesService;

    @Test
    @DisplayName("List courses - Successful")
    public void listCourses_Successful() {
        // Arrange
        List<CourseJpa> coursesJpaList = createCourseJpaList();
        List<Course> expectedCourses = coursesJpaList.stream()
                .map(CourseJpa::toCourse)
                .collect(Collectors.toList());

        when(courseRepository.findAll()).thenReturn(coursesJpaList);

        // Act
        List<Course> actualCourses = listCoursesService.listCourses();

        // Assert
        assertThat(actualCourses).isNotNull();
        assertThat(actualCourses)
                .hasSize(expectedCourses.size())
                .containsAll(expectedCourses);
    }

    private List<CourseJpa> createCourseJpaList() {
        List<CourseJpa> courses = new ArrayList<>();

        courses.add(new CourseJpa(1L, "Engineering", "University", 3.5));
        courses.add(new CourseJpa(2L, "Doctorate", "University", 4d));

        return courses;
    }
}