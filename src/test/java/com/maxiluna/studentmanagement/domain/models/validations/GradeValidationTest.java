package com.maxiluna.studentmanagement.domain.models.validations;

import com.maxiluna.studentmanagement.domain.models.Grade;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class GradeValidationTest {
    private Grade grade;

    private LocalValidatorFactoryBean validatorFactoryBean;

    @BeforeEach
    public void setUp() {
        grade = new Grade();
        validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.afterPropertiesSet();
    }

    @AfterEach
    public void tearDown() {
        if (validatorFactoryBean != null) {
            validatorFactoryBean.destroy();
        }
    }

    public Set<ConstraintViolation<Grade>> validate(Grade gradeToValidate) {
        return validatorFactoryBean.validate(gradeToValidate);
    }

    @Test
    @DisplayName("Validate 'description' property - Not blank")
    public void validateDescription_NotBlank() {
        // Arrange
        grade.setDescription("");

        // Act
        Set<ConstraintViolation<Grade>> violations = validate(grade);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Description must not be blank");
    }

    @Test
    @DisplayName("Validate 'description' property - Size")
    public void validateDescription_Size() {
        // Arrange
        grade.setDescription("A");

        // Act
        Set<ConstraintViolation<Grade>> violations = validate(grade);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Description must be between 2 and 500 characters");
    }

    @Test
    @DisplayName("Validate 'score' property - Not null")
    public void validateScore_NotNull() {
        // Arrange

        // Act
        Set<ConstraintViolation<Grade>> violations = validate(grade);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Score must not be null");
    }

    @Test
    @DisplayName("Validate 'score' property - Decimal min")
    public void validateScore_DecimalMin() {
        // Arrange
        grade.setScore(-1.0);

        // Act
        Set<ConstraintViolation<Grade>> violations = validate(grade);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Score must not be less than 0");
    }

    @Test
    @DisplayName("Validate 'score' property - Decimal max")
    public void validateScore_DecimalMax() {
        // Arrange
        grade.setScore(101.0);

        // Act
        Set<ConstraintViolation<Grade>> violations = validate(grade);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Score must be greater than 100");
    }
}
