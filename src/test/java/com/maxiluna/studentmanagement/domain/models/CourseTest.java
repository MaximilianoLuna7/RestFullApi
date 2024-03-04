package com.maxiluna.studentmanagement.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CourseTest {

    private final String name = "Math";
    private final String institution = "University";
    private final Double years = 3.5;
    @Test
    @DisplayName("Create Course instance")
    public void createCourse_SuccessfulInstantiation() {
        // Arrange & Act
        Course course = new Course();

        // Assert
        assertThat(course).isNotNull();
    }

    @Test
    @DisplayName("Set and get 'name' property - Successful")
    public void setAndGetName_Successful() {
        // Arrange
        Course course = new Course();

        // Act
        course.setName(name);

        // Assert
        assertThat(course.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("Set and get 'institutionName' property - Successful")
    public void setAndGetInstitutionName_Successful() {
        // Arrange
        Course course = new Course();

        // Act
        course.setInstitutionName(institution);

        // Assert
        assertThat(course.getInstitutionName()).isEqualTo(institution);
    }

    @Test
    @DisplayName("Set and get 'durationInYears' property - Successful")
    public void setAndGetDurationInYears_Successful() {
        // Arrange
        Course course = new Course();

        // Act
        course.setDurationInYears(years);

        // Assert
        assertThat(course.getDurationInYears()).isEqualTo(years);
    }

    @Test
    @DisplayName("Equality test")
    public void equalityTest() {
        // Arrange
        Course course = new Course();
        Course equalCourse = new Course();

        // Act
        course.setName(name);
        course.setInstitutionName(institution);
        course.setDurationInYears(years);

        equalCourse.setName(name);
        equalCourse.setInstitutionName(institution);
        equalCourse.setDurationInYears(years);

        // Assert
        assertThat(course).isEqualTo(equalCourse);
    }

    @Test
    @DisplayName("Builder test")
    public void builderTest() {
        // Arrange & Act
        Course builtCourse = Course.builder()
                .name(name)
                .institutionName(institution)
                .durationInYears(years)
                .build();

        // Assert
        assertThat(builtCourse.getName()).isEqualTo(name);
        assertThat(builtCourse.getInstitutionName()).isEqualTo(institution);
        assertThat(builtCourse.getDurationInYears()).isEqualTo(years);
    }
}