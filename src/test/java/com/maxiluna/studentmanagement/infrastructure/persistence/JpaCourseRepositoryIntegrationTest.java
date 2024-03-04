package com.maxiluna.studentmanagement.infrastructure.persistence;

import com.maxiluna.studentmanagement.infrastructure.entities.CourseJpa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class JpaCourseRepositoryIntegrationTest {

    @Autowired
    private JpaCourseRepository courseRepository;

    @Test
    @DisplayName("Save Course - Successful")
    public void saveCourse_Successful() {
        // Arrange
        CourseJpa course = createCourseJpa();

        // Act
        CourseJpa savedCourse = courseRepository.save(course);

        // Assert
        assertThat(savedCourse.getId()).isNotNull();
        assertThat(savedCourse.getName()).isEqualTo(course.getName());
        assertThat(savedCourse.getInstitutionName()).isEqualTo(course.getInstitutionName());
        assertThat(savedCourse.getDurationInYears()).isEqualTo(course.getDurationInYears());
    }

    @Test
    @DisplayName("Find Course by ID - Successful")
    public void findCourseById_Successful() {
        // Arrange
        CourseJpa course = createCourseJpa();
        CourseJpa savedCourse = courseRepository.save(course);

        // Act
        Optional<CourseJpa> foundCourseOptional = courseRepository.findById(savedCourse.getId());

        // Assert
        assertThat(foundCourseOptional).isPresent();
        assertThat(foundCourseOptional.get().getId()).isEqualTo(savedCourse.getId());
    }

    @Test
    @DisplayName("Find Course by ID - Not found")
    public void findCourseById_NotFound() {
        // Arrange
        Long nonExistentId = 999L;

        // Act
        Optional<CourseJpa> foundCourseOptional = courseRepository.findById(nonExistentId);

        // Assert
        assertThat(foundCourseOptional).isEmpty();
    }

    @Test
    @DisplayName("Find all Courses - Successful")
    public void findAllCourses_Successful() {
        // Arrange
        CourseJpa course1 = createCourseJpa();
        CourseJpa course2 = CourseJpa.builder()
                .name("Doctorate")
                .institutionName("University")
                .durationInYears(5d)
                .build();

        courseRepository.save(course1);
        courseRepository.save(course2);

        // Act
        List<CourseJpa> foundCourses = courseRepository.findAll();

        // Assert
        assertThat(foundCourses)
                .isNotNull()
                .hasSize(2);
        assertThat(foundCourses).contains(course1, course2);
    }

    @Test
    @DisplayName("Delete Course - Successful")
    public void deleteCourse_Successful() {
        // Arrange
        CourseJpa course = createCourseJpa();
        CourseJpa savedCourse = courseRepository.save(course);

        // Act
        courseRepository.delete(savedCourse);

        // Assert
        Optional<CourseJpa> deletedCourseOptional = courseRepository.findById(savedCourse.getId());
        assertThat(deletedCourseOptional).isEmpty();
    }

    private CourseJpa createCourseJpa() {
        return CourseJpa.builder()
                .name("Engineering")
                .institutionName("University")
                .durationInYears(3.5)
                .build();
    }
}