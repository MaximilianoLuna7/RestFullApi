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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCourseDataServiceTest {

    @Mock
    private JpaCourseRepository courseRepository;

    @InjectMocks
    private UpdateCourseDataService updateCourseDataService;

    @Test
    @DisplayName("Update course - Successful")
    public void updateCourse_Successful() {
        // Arrange
        Course updatedCourse = createUpdatedCourse();
        CourseJpa courseJpa = createCourseJpa();
        Long courseId = courseJpa.getId();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseJpa));

        // Act
        updateCourseDataService.execute(courseId, updatedCourse);

        // Verify
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).save(any(CourseJpa.class));
    }

    @Test
    @DisplayName("Update course with invalid id - Throws Exception")
    public void updateCourseWithInvalidId_ThrowsException() {
        // Arrange
        Course updatedCourse = createUpdatedCourse();
        Long invalidId = 0L;

        // Act & Assert
        assertThatThrownBy(() -> updateCourseDataService.execute(invalidId, updatedCourse))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid course ID: " + invalidId);

        // Verify
        verifyNoInteractions(courseRepository);
    }

    @Test
    @DisplayName("Update non existent course - Throws Exception")
    public void updateNonExistentCoursed_ThrowsException() {
        // Arrange
        Course updatedCourse = createUpdatedCourse();
        Long nonExistentId = 999L;

        when(courseRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> updateCourseDataService.execute(nonExistentId, updatedCourse))
                .isInstanceOf(CourseNotFoundException.class)
                .hasMessageContaining("Course not found with ID: " + nonExistentId);

        // Verify
        verify(courseRepository, times(1)).findById(nonExistentId);
        verifyNoMoreInteractions(courseRepository);
    }

    private CourseJpa createCourseJpa() {
        return CourseJpa.builder()
                .id(1L)
                .name("Engineering")
                .institutionName("University")
                .durationInYears(3.5)
                .build();
    }

    private Course createUpdatedCourse() {
        return Course.builder()
                .id(1L)
                .name("Updated")
                .institutionName("Updated university")
                .durationInYears(4d)
                .build();
    }
}