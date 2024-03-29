package com.maxiluna.studentmanagement.domain.models.validations;

import com.maxiluna.studentmanagement.domain.models.Student;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentValidationTest {
    private Student student;

    private LocalValidatorFactoryBean validatorFactoryBean;

    @BeforeEach
    public void setUp() {
        student = new Student();
        validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.afterPropertiesSet();
    }

    @AfterEach
    public void tearDown() {
        if (validatorFactoryBean != null) {
            validatorFactoryBean.destroy();
        }
    }

    public Set<ConstraintViolation<Student>> validate(Student studentToValidate) {
        return validatorFactoryBean.validate(studentToValidate);
    }

    @Test
    @DisplayName("Validate 'firstName' property - Not blank")
    public void validateFirstName_NotBlank() {
        // Arrange
        student.setFirstName("");

        // Act
        Set<ConstraintViolation<Student>> violations = validate(student);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("FirstName must not be blank");
    }

    @Test
    @DisplayName("Validate 'firstName' property - Size")
    public void validateFirstName_Size() {
        // Arrange
        student.setFirstName("A");

        // Act
        Set<ConstraintViolation<Student>> violations = validate(student);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("FirstName must be between 2 and 100 characters");
    }

    @Test
    @DisplayName("Validate 'lastName' property - Not blank")
    public void validateLastName_NotBlank() {
        // Arrange
        student.setLastName("");

        // Act
        Set<ConstraintViolation<Student>> violations = validate(student);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("LastName must not be blank");
    }

    @Test
    @DisplayName("Validate 'lastName' property - Size")
    public void validateLastName_Size() {
        // Arrange
        student.setLastName("A");

        // Act
        Set<ConstraintViolation<Student>> violations = validate(student);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("LastName must be between 2 and 100 characters");
    }

    @Test
    @DisplayName("Validate 'email' property - Invalid format")
    public void validateEmail_InvalidFormat() {
        // Arrange
        student.setEmail("jane.smith");

        // Act
        Set<ConstraintViolation<Student>> violations = validate(student);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Invalid email format");
    }

    @Test
    @DisplayName("Validate 'email' property - Size")
    public void validateEmail_Size() {
        // Arrange
        student.setEmail("A");

        // Act
        Set<ConstraintViolation<Student>> violations = validate(student);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Email must be between 2 and 255 characters");
    }

    @Test
    @DisplayName("Validate 'birthDate' property - Past")
    public void validateBirthDate_Past() {
        // Arrange
        student.setBirthDate(LocalDate.now().plusYears(1));

        // Act
        Set<ConstraintViolation<Student>> violations = validate(student);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Birth date must be in the past");
    }

    @Test
    @DisplayName("Validate 'dni' property - Pattern")
    public void validateDni_Pattern() {
        // Arrange
        student.setDni("1234");

        // Act
        Set<ConstraintViolation<Student>> violations = validate(student);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("DNI must be an 8-digit number");
    }

    @Test
    @DisplayName("Validate 'city' property - Size")
    public void validateCity_Size() {
        // Arrange
        student.setCity("A");

        // Act
        Set<ConstraintViolation<Student>> violations = validate(student);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("City name must be between 2 and 100 characters");
    }

    @Test
    @DisplayName("Validate 'admissionYear' property - Min")
    public void validateAdmissionYear_Min() {
        // Arrange
        student.setAdmissionYear(1999);

        // Act
        Set<ConstraintViolation<Student>> violations = validate(student);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Admission year must be greater than or equal to 2000");
    }
}
