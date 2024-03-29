package com.maxiluna.studentmanagement.domain.models.validations;

import com.maxiluna.studentmanagement.domain.models.Attendance;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendanceValidationTest {
    private Attendance attendance;

    private LocalValidatorFactoryBean validatorFactoryBean;

    @BeforeEach
    public void setUp() {
        attendance = new Attendance();
        validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.afterPropertiesSet();
    }

    @AfterEach
    public void tearDown() {
        if (validatorFactoryBean != null) {
            validatorFactoryBean.destroy();
        }
    }

    public Set<ConstraintViolation<Attendance>> validate(Attendance attendanceToValidate) {
        return validatorFactoryBean.validate(attendanceToValidate);
    }

    @Test
    @DisplayName("Validate 'student' property - Not null")
    public void validateStudent_NotNull() {
        // Arrange

        // Act
        Set<ConstraintViolation<Attendance>> violations = validate(attendance);

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
        Set<ConstraintViolation<Attendance>> violations = validate(attendance);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Subject must not be null");
    }

    @Test
    @DisplayName("Validate 'classRecord' property - Not null")
    public void validateClassRecord_NotNull() {
        // Arrange

        // Act
        Set<ConstraintViolation<Attendance>> violations = validate(attendance);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Class record must not be null");
    }

    @Test
    @DisplayName("Validate 'attendanceStatus' property - Not null")
    public void validateAttendanceStatus_NotNull() {
        // Arrange

        // Act
        Set<ConstraintViolation<Attendance>> violations = validate(attendance);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Attendance status must not be null");
    }
}
