package com.maxiluna.studentmanagement.application.services.course;

import com.maxiluna.studentmanagement.domain.exceptions.CourseNotFoundException;
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
class DeleteCourseServiceTest {

    @Mock
    private JpaCourseRepository courseRepository;

    @InjectMocks
    private DeleteCourseService deleteCourseService;

    @Test
    @DisplayName("Delete course information - Successful")
    public void deleteCourse_Successful() {
        // Arrange
        CourseJpa courseJpa = createCourseJpa();
        Long courseId = courseJpa.getId();;

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseJpa));

        // Act
        deleteCourseService.execute(courseId);

        // Verify
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).delete(courseJpa);
    }

    @Test
    @DisplayName("Delete non existent course - Throws Exception")
    public void deleteNonExistentCourse_ThrowsException() {
        // Arrange
        Long nonExistentId = 999L;

        when(courseRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> deleteCourseService.execute(nonExistentId))
                .isInstanceOf(CourseNotFoundException.class)
                        .hasMessageContaining("Course not found with ID: " + nonExistentId);

        // Verify
        verify(courseRepository, times(1)).findById(nonExistentId);
        verifyNoMoreInteractions(courseRepository);
    }

    @Test
    @DisplayName("Delete course with invalid id - Throws Exception")
    public void deleteCourseWithInvalidId_ThrowsException() {
        // Arrange
        Long invalidId = 0L;

        // Act & Assert
        assertThatThrownBy(() -> deleteCourseService.execute(invalidId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid course ID: " + invalidId);

        // Verify
        verifyNoInteractions(courseRepository);
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