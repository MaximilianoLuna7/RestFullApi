package com.maxiluna.studentmanagement.domain.models.validations;

import com.maxiluna.studentmanagement.domain.models.Course;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CourseValidationTest {

    private LocalValidatorFactoryBean validatorFactoryBean;

    private final String blankString = "";
    private final String veryShortString = "A";

    @BeforeEach
    public void setUp() {
        validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.afterPropertiesSet();
    }

    @AfterEach
    public void tearDown() {
        if (validatorFactoryBean != null) {
            validatorFactoryBean.destroy();
        }
    }

    public Set<ConstraintViolation<Course>> validate(Course courseToValidate) {
        return validatorFactoryBean.validate(courseToValidate);
    }

    @Test
    @DisplayName("Validate 'name' property - Not blank")
    public void validateName_NotBlank() {
        // Arrange
        Course course = new Course();

        // Act
        course.setName(blankString);
        Set<ConstraintViolation<Course>> violations = validate(course);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Name must not be blank");
    }

    @Test
    @DisplayName("Validate 'name' property - Size")
    public void validateName_Size() {
        // Arrange
        Course course = new Course();

        // Act
        course.setName(veryShortString);
        Set<ConstraintViolation<Course>> violations = validate(course);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Name must be between 2 and 100 characters");
    }

    @Test
    @DisplayName("Validate 'institutionName' property - Not blank")
    public void validateInstitutionName_NotBlank() {
        // Arrange
        Course course = new Course();

        // Act
        course.setInstitutionName(blankString);
        Set<ConstraintViolation<Course>> violations = validate(course);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Institution name must not be blank");
    }

    @Test
    @DisplayName("Validate 'institutionName' property - Size")
    public void validateInstitutionName_Size() {
        // Arrange
        Course course = new Course();

        // Act
        course.setInstitutionName(veryShortString);
        Set<ConstraintViolation<Course>> violations = validate(course);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Institution name must be between 2 and 100 characters");
    }

    @Test
    @DisplayName("Validate 'durationInYears' property - Not null")
    public void validateDurationInYears_NotNull() {
        // Arrange
        Course course = new Course();

        // Act
        Set<ConstraintViolation<Course>> violations = validate(course);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Duration years most not be null");
    }

    @Test
    @DisplayName("Validate 'durationInYears' property - Positive")
    public void validateDurationInYears_Positive() {
        // Arrange
        Course course = new Course();

        // Act
        course.setDurationInYears(0d);
        Set<ConstraintViolation<Course>> violations = validate(course);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Duration years must be a positive number");
    }
}
