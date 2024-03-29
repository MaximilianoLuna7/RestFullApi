package com.maxiluna.studentmanagement.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class StudentTest {
    private final String firstName = "Jane";
    private final String lastName = "Smith";
    private final String email = "jane.smith@example.com";
    private final LocalDate birthDate = LocalDate.of(2000, 2,2);
    private final String dni = "12345678";
    private final String city = "Any city";
    private final Integer admissionYear = 2022;

    @Test
    @DisplayName("Create Student instance")
    public void createStudent_SuccessfulInstantiation() {
        // Arrange & Act
        Student student = new Student();

        // Assert
        assertThat(student).isNotNull();
    }

    @Test
    @DisplayName("Set and get 'firstName' property - Successful")
    public void setAndGetFirstName_Successful() {
        // Arrange
        Student student = new Student();

        // Act
        student.setFirstName(firstName);

        // Assert
        assertThat(student.getFirstName()).isEqualTo(firstName);
    }

    @Test
    @DisplayName("Set and get 'lastName' property - Successful")
    public void setAndGetLastName_Successful() {
        // Arrange
        Student student = new Student();

        // Act
        student.setLastName(lastName);

        // Assert
        assertThat(student.getLastName()).isEqualTo(lastName);
    }

    @Test
    @DisplayName("Set and get 'email' property - Successful")
    public void setAndGetEmail_Successful() {
        // Arrange
        Student student = new Student();

        // Act
        student.setEmail(email);

        // Assert
        assertThat(student.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("Set and get 'birthDate' property - Successful")
    public void setAndGetBirthDate_Successful() {
        // Arrange
        Student student = new Student();

        // Act
        student.setBirthDate(birthDate);

        // Assert
        assertThat(student.getBirthDate()).isEqualTo(birthDate);
    }

    @Test
    @DisplayName("Set and get 'dni' property - Successful")
    public void setAndGetDni_Successful() {
        // Arrange
        Student student = new Student();

        // Act
        student.setDni(dni);

        // Assert
        assertThat(student.getDni()).isEqualTo(dni);
    }

    @Test
    @DisplayName("Set and get 'city' property - Successful")
    public void setAndGetCity_Successful() {
        // Arrange
        Student student = new Student();

        // Act
        student.setCity(city);

        // Assert
        assertThat(student.getCity()).isEqualTo(city);
    }

    @Test
    @DisplayName("Set and get 'admissionYear' property - Successful")
    public void setAndGetAdmissionYear_Successful() {
        // Arrange
        Student student = new Student();

        // Act
        student.setAdmissionYear(admissionYear);

        // Assert
        assertThat(student.getAdmissionYear()).isEqualTo(admissionYear);
    }

    @Test
    @DisplayName("Equality test")
    public void equalityTest() {
        // Arrange
        Student student = new Student();
        Student equalStudent = new Student();

        // Act
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setBirthDate(birthDate);
        student.setDni(dni);
        student.setCity(city);
        student.setAdmissionYear(admissionYear);

        equalStudent.setFirstName(firstName);
        equalStudent.setLastName(lastName);
        equalStudent.setEmail(email);
        equalStudent.setBirthDate(birthDate);
        equalStudent.setDni(dni);
        equalStudent.setCity(city);
        equalStudent.setAdmissionYear(admissionYear);

        // Assert
        assertThat(student).isEqualTo(equalStudent);
    }

    @Test
    @DisplayName("Builder test")
    public void builderTest() {
        // Arrange & Act
        Student builtStudent = Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .birthDate(birthDate)
                .dni(dni)
                .city(city)
                .admissionYear(admissionYear)
                .build();

        // Assert
        assertThat(builtStudent.getFirstName()).isEqualTo(firstName);
        assertThat(builtStudent.getLastName()).isEqualTo(lastName);
        assertThat(builtStudent.getEmail()).isEqualTo(email);
        assertThat(builtStudent.getBirthDate()).isEqualTo(birthDate);
        assertThat(builtStudent.getDni()).isEqualTo(dni);
        assertThat(builtStudent.getCity()).isEqualTo(city);
        assertThat(builtStudent.getAdmissionYear()).isEqualTo(admissionYear);
    }
}