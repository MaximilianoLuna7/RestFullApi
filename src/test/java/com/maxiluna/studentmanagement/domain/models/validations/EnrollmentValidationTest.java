package com.maxiluna.studentmanagement.domain.models.validations;

import com.maxiluna.studentmanagement.domain.models.Enrollment;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class EnrollmentValidationTest {
    private Enrollment enrollment;

    private LocalValidatorFactoryBean validatorFactoryBean;

    @BeforeEach
    public void setUp() {
        enrollment = new Enrollment();
        validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.afterPropertiesSet();
    }

    @AfterEach
    public void tearDown() {
        if (validatorFactoryBean != null) {
            validatorFactoryBean.destroy();
        }
    }

    public Set<ConstraintViolation<Enrollment>> validate(Enrollment enrollmentToValidate) {
        return validatorFactoryBean.validate(enrollmentToValidate);
    }

    @Test
    @DisplayName("Validate 'student' property - Not null")
    public void validateStudent_NotNull() {
        // Arrange

        // Act
        Set<ConstraintViolation<Enrollment>> violations = validate(enrollment);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Student must not be null");
    }

    @Test
    @DisplayName("Validate 'subject' property - Not null")
    public void validateSubject_NotNull() {
        // Arrange

        // Act
        Set<ConstraintViolation<Enrollment>> violations = validate(enrollment);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Subject must not be null");
    }

    @Test
    @DisplayName("Validate 'studentStatus' property - Not null")
    public void validateStudentStatus_NotNull() {
        // Arrange

        // Act
        Set<ConstraintViolation<Enrollment>> violations = validate(enrollment);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Student status must not be null");
    }
}
