package com.maxiluna.studentmanagement.domain.models.validations;

import com.maxiluna.studentmanagement.domain.models.Subject;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SubjectValidationTest {

    private LocalValidatorFactoryBean validatorFactoryBean;

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

    public Set<ConstraintViolation<Subject>> validate(Subject subjectToValidate) {
        return validatorFactoryBean.validate(subjectToValidate);
    }

    @Test
    @DisplayName("Validate 'name' property - Not blank")
    public void validateName_NotBlank() {
        // Arrange
        Subject subject = new Subject();

        // Act
        subject.setName("");
        Set<ConstraintViolation<Subject>> violations = validate(subject);

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
        Subject subject = new Subject();

        // Act
        subject.setName("A");
        Set<ConstraintViolation<Subject>> violations = validate(subject);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Name must be between 2 and 100 characters");
    }

    @Test
    @DisplayName("Validate 'academicYear' property - Not null")
    public void validateAcademicYear_NotNull() {
        // Arrange
        Subject subject = new Subject();

        // Act
        Set<ConstraintViolation<Subject>> violations = validate(subject);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Academic year most not be null");
    }

    @Test
    @DisplayName("Validate 'academicYear' property - Min")
    public void validateAcademicYear_Min() {
        // Arrange
        Subject subject = new Subject();

        // Act
        subject.setAcademicYear(1900);
        Set<ConstraintViolation<Subject>> violations = validate(subject);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Academic year must be greater than or equal to 2000");
    }
}
