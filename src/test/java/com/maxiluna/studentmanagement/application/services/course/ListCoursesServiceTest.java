package com.maxiluna.studentmanagement.application.services.course;

import com.maxiluna.studentmanagement.domain.models.Course;
import com.maxiluna.studentmanagement.domain.models.Subject;
import com.maxiluna.studentmanagement.infrastructure.entities.CourseJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaCourseRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaSubjectRepository;
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
import static org.mockito.Mockito.*;

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

        // Verify
        verify(courseRepository, times(1)).findAll();
    }

    private List<CourseJpa> createCourseJpaList() {
        List<CourseJpa> courses = new ArrayList<>();

        CourseJpa course1 = CourseJpa.builder()
                .id(1L)
                .name("Engineering")
                .institutionName("University")
                .durationInYears(3.5)
                .build();
        CourseJpa course2 = CourseJpa.builder()
                .id(2L)
                .name("Doctorate")
                .institutionName("University")
                .durationInYears(4d)
                .build();

        courses.add(course1);
        courses.add(course2);

        return courses;
    }
}