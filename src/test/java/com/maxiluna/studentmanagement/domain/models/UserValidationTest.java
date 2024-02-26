package com.maxiluna.studentmanagement.domain.models;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserValidationTest {

    private User user;

    private LocalValidatorFactoryBean validatorFactoryBean;

    @BeforeEach
    public void setUp() {
        user = new User();
        validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.afterPropertiesSet();
    }

    @AfterEach
    public void tearDown() {
        if (validatorFactoryBean != null) {
            validatorFactoryBean.destroy();
        }
    }

    public Set<ConstraintViolation<User>> validate(User userToValidate) {
        return validatorFactoryBean.validate(userToValidate);
    }

    @Test
    @DisplayName("Validate 'email' property - Format")
    public void validateEmail_Format() {
        // Arrange
        String invalidFormatEmail = "john.doe";

        // Act
        user.setEmail(invalidFormatEmail);
        Set<ConstraintViolation<User>> violations = validate(user);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Invalid email format");
    }

    @Test
    @DisplayName("Validate 'email' property - Not Blank")
    public void validateEmail_NotBlank() {
        // Arrange
        String blankEmail = "";

        // Act
        user.setEmail(blankEmail);
        Set<ConstraintViolation<User>> violations = validate(user);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Email must not be blank");
    }

    @Test
    @DisplayName("Validate 'email' property - Size")
    public void validateEmail_Size() {
        // Arrange
        String veryShortEmail = "A";

        // Act
        user.setEmail(veryShortEmail);
        Set<ConstraintViolation<User>> violations = validate(user);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Email must be between 2 and 255 characters");
    }

    @Test
    @DisplayName("Validate 'password' property - Not Blank")
    public void validatePassword_NotBlank() {
        // Arrange
        String blankPassword = "";

        // Act
        user.setPassword(blankPassword);
        Set<ConstraintViolation<User>> violations = validate(user);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Password must not be blank");
    }

    @Test
    @DisplayName("Validate 'firstName' property - Size")
    public void validateFirstName_Size() {
        // Arrange
        String veryShortFirstName = "A";

        // Act
        user.setFirstName(veryShortFirstName);
        Set<ConstraintViolation<User>> violations = validate(user);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("First name must be between 2 and 100 characters");
    }

    @Test
    @DisplayName("Validate 'lastName' property - Size")
    public void validateLastName_Size() {
        // Arrange
        String veryShortLastName = "A";

        // Act
        user.setLastName(veryShortLastName);
        Set<ConstraintViolation<User>> violations = validate(user);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Last name must be between 2 and 100 characters");
    }

    @Test
    @DisplayName("Validate 'birthDate' property - Past")
    public void validateBirthDate_Past() {
        // Arrange
        LocalDate futureDate = LocalDate.now().plusYears(1);

        // Act
        user.setBirthDate(futureDate);
        Set<ConstraintViolation<User>> violations = validate(user);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Birth date must be in the past");
    }

    @Test
    @DisplayName("Validate 'role' property - Not null")
    public void validateRole_NotNull() {
        // Arrange
        // Not set role

        // Act
        Set<ConstraintViolation<User>> violations = validate(user);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Role must not be null");
    }
}
