package com.maxiluna.studentmanagement.domain.models.validations;

import com.maxiluna.studentmanagement.domain.models.ClassRecord;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassRecordValidationTest {
    private ClassRecord classRecord;

    private LocalValidatorFactoryBean validatorFactoryBean;

    @BeforeEach
    public void setUp() {
        classRecord = new ClassRecord();
        validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.afterPropertiesSet();
    }

    @AfterEach
    public void tearDown() {
        if (validatorFactoryBean != null) {
            validatorFactoryBean.destroy();
        }
    }

    public Set<ConstraintViolation<ClassRecord>> validate(ClassRecord classToValidate) {
        return validatorFactoryBean.validate(classToValidate);
    }

    @Test
    @DisplayName("Validate 'topic' property - Not blank")
    public void validateTopic_NotBlank() {
        // Arrange
        classRecord.setTopic("");

        // Act
        Set<ConstraintViolation<ClassRecord>> violations = validate(classRecord);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Topic must not be blank");
    }

    @Test
    @DisplayName("Validate 'topic' property - Size")
    public void validateTopic_Size() {
        // Arrange
        classRecord.setTopic("A");

        // Act
        Set<ConstraintViolation<ClassRecord>> violations = validate(classRecord);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Topic must not be between 2 and 255");
    }

    @Test
    @DisplayName("Validate 'activities' property - Size")
    public void validateActivities_Size() {
        // Arrange
        classRecord.setActivities("A");

        // Act
        Set<ConstraintViolation<ClassRecord>> violations = validate(classRecord);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Activities must not be between 2 and 500");
    }

    @Test
    @DisplayName("Validate 'date' property - Not null")
    public void validateDate_NotNull() {
        // Arrange

        // Act
        Set<ConstraintViolation<ClassRecord>> violations = validate(classRecord);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Date must not be null");
    }

    @Test
    @DisplayName("Validate 'subject' property - Not null")
    public void validateSubject_NotNull() {
        // Arrange

        // Act
        Set<ConstraintViolation<ClassRecord>> violations = validate(classRecord);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Subject must not be null");
    }
}
