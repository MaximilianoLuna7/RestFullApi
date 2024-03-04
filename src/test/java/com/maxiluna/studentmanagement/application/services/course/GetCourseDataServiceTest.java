package com.maxiluna.studentmanagement.application.services.course;

import com.maxiluna.studentmanagement.domain.exceptions.CourseNotFoundException;
import com.maxiluna.studentmanagement.domain.models.Course;
import com.maxiluna.studentmanagement.infrastructure.entities.CourseJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaCourseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCourseDataServiceTest {

    @Mock
    private JpaCourseRepository courseRepository;

    @InjectMocks
    private GetCourseDataService getCourseDataService;

    @Test
    @DisplayName("Get course data - Successful")
    public void getCourseData_Successful() {
        // Arrange
        CourseJpa courseJpa = createCourseJpa();
        Long courseId = courseJpa.getId();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseJpa));

        // Act
        Course course = getCourseDataService.getCourseData(courseId);

        // Assert
        assertThat(course).isNotNull();
        assertThat(course.getId()).isEqualTo(courseId);

        // Verify
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    @DisplayName("Get course data with non existent course id - Throws exception")
    public void getCourseDataWthNonExistentId_ThrowsException() {
        // Arrange
        Long nonExistentId = 999L;

        when(courseRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> getCourseDataService.getCourseData(nonExistentId))
                .isInstanceOf(CourseNotFoundException.class)
                        .hasMessageContaining("Course not found with ID: " + nonExistentId);

        // Verify
        verify(courseRepository, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("Get course data with invalid course id - Throws exception")
    public void getCourseDataWthInvalidId_ThrowsException() {
        // Arrange
        Long invalidId = 0L;

        // Act & Assert
        assertThatThrownBy(() -> getCourseDataService.getCourseData(invalidId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid course ID: " + invalidId);
    }

    private CourseJpa createCourseJpa() {
        return CourseJpa.builder()
                .id(1L)
                .name("Engineering")
                .institutionName("University")
                .durationInYears(3.5)
                .build();
    }
}