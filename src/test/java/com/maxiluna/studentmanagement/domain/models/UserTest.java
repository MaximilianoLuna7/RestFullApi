package com.maxiluna.studentmanagement.domain.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    private User user;
    private final Long userId = 1L;
    private final String userEmail = "john.doe@example.com";
    private final String userPassword = "password123";
    private final String userFirstName = "John";
    private final String userLastName = "Doe";
    private final LocalDate userBirthDate = LocalDate.of(2000, 2, 5);
    private final UserRole userAdmin = UserRole.ADMIN;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    @DisplayName("Create User instance")
    public void createUser_SuccessfulInstantiation() {
        // Act & Assert
        assertThat(user).isNotNull();
    }

    @Test
    @DisplayName("Set and get 'email' property - Successful")
    public void setAndGetEmail_Successful() {
        // Arrange & Act
        user.setEmail(userEmail);

        // Assert
        assertThat(user.getEmail()).isEqualTo(userEmail);
    }

    @Test
    @DisplayName("Set and get 'firstName' property - Successful")
    public void setAndGetFirstName_Successful() {
        // Arrange & Act
        user.setFirstName(userFirstName);

        // Assert
        assertThat(user.getFirstName()).isEqualTo(userFirstName);
    }

    @Test
    @DisplayName("Set and get 'lastName' property - Successful")
    public void setAndGetLastName_Successful() {
        // Arrange & Act
        user.setLastName(userLastName);

        // Assert
        assertThat(user.getLastName()).isEqualTo(userLastName);
    }

    @Test
    @DisplayName("Set and get 'password' property - Successful")
    public void setAndGetPassword_Successful() {
        // Arrange & Act
        user.setPassword(userPassword);

        // Assert
        assertThat(user.getPassword()).isEqualTo(userPassword);
    }

    @Test
    @DisplayName("Set and get 'birthDate' property - Successful")
    public void setAndGetBirthDate_Successful() {
        // Arrange & Act
        user.setBirthDate(userBirthDate);

        // Assert
        assertThat(user.getBirthDate()).isEqualTo(userBirthDate);
    }

    @Test
    @DisplayName("Set and get 'role' property - Successful")
    public void setAndGetRole_Successful() {
        // Arrange & Act
        user.setRole(userAdmin);

        // Assert
        assertThat(user.getRole()).isEqualTo(userAdmin);
    }

    @Test
    @DisplayName("Equality test")
    public void equalityTest() {
        // Arrange
        user.setId(userId);
        user.setEmail(userEmail);
        user.setPassword(userPassword);
        user.setFirstName(userFirstName);
        user.setLastName(userLastName);
        user.setBirthDate(userBirthDate);
        user.setRole(userAdmin);

        // Act
        User equalUser = User.builder()
                .id(userId)
                .email(userEmail)
                .password(userPassword)
                .firstName(userFirstName)
                .lastName(userLastName)
                .birthDate(userBirthDate)
                .role(userAdmin)
                .subjects(new ArrayList<>())
                .build();

        // Assert
        assertThat(user).isEqualTo(equalUser);
    }

    @Test
    @DisplayName("Builder test")
    public void builderTest() {
        // Arrange & Act
        User builtUser = User.builder()
                .id(userId)
                .email(userEmail)
                .password(userPassword)
                .firstName(userFirstName)
                .lastName(userLastName)
                .birthDate(userBirthDate)
                .role(userAdmin)
                .build();

        // Assert
        assertThat(builtUser.getId()).isEqualTo(userId);
        assertThat(builtUser.getEmail()).isEqualTo(userEmail);
        assertThat(builtUser.getPassword()).isEqualTo(userPassword);
        assertThat(builtUser.getFirstName()).isEqualTo(userFirstName);
        assertThat(builtUser.getLastName()).isEqualTo(userLastName);
        assertThat(builtUser.getBirthDate()).isEqualTo(userBirthDate);
        assertThat(builtUser.getRole()).isEqualTo(userAdmin);
    }
}
