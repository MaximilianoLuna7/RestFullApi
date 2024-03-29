package com.maxiluna.studentmanagement.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnrollmentTest {
    private final Student student = new Student();
    private final Subject subject = new Subject();
    private final StudentStatus studentStatus = StudentStatus.REGULAR;

    @Test
    @DisplayName("Create Enrollment instance")
    public void createStudent_SuccessfulInstantiation() {
        // Arrange & Act
        Enrollment enrollment = new Enrollment();

        // Assert
        assertThat(enrollment).isNotNull();
    }

    @Test
    @DisplayName("Set and get 'student' property - Successful")
    public void setAndGetStudent_Successful() {
        // Arrange
        Enrollment enrollment = new Enrollment();

        // Act
        enrollment.setStudent(student);

        // Assert
        assertThat(enrollment.getStudent()).isEqualTo(student);
    }

    @Test
    @DisplayName("Set and get 'subject' property - Successful")
    public void setAndGetSubject_Successful() {
        // Arrange
        Enrollment enrollment = new Enrollment();

        // Act
        enrollment.setSubject(subject);

        // Assert
        assertThat(enrollment.getSubject()).isEqualTo(subject);
    }

    @Test
    @DisplayName("Set and get 'studentStatus' property - Successful")
    public void setAndGetStudentStatus_Successful() {
        // Arrange
        Enrollment enrollment = new Enrollment();

        // Act
        enrollment.setStudentStatus(studentStatus);

        // Assert
        assertThat(enrollment.getStudentStatus()).isEqualTo(studentStatus);
    }

    @Test
    @DisplayName("Equality test")
    public void equalityTest() {
        // Arrange
        Enrollment enrollment = new Enrollment();
        Enrollment equalEnrollment = new Enrollment();

        // Act
        enrollment.setStudent(student);
        enrollment.setSubject(subject);
        enrollment.setStudentStatus(studentStatus);

        equalEnrollment.setStudent(student);
        equalEnrollment.setSubject(subject);
        equalEnrollment.setStudentStatus(studentStatus);

        // Assert
        assertThat(enrollment).isEqualTo(equalEnrollment);
    }

    @Test
    @DisplayName("Builder test")
    public void builderTest() {
        // Arrange & Act
        Enrollment builtEnrollment = Enrollment.builder()
                .student(student)
                .subject(subject)
                .studentStatus(studentStatus)
                .build();

        // Assert
        assertThat(builtEnrollment.getStudent()).isEqualTo(student);
        assertThat(builtEnrollment.getSubject()).isEqualTo(subject);
        assertThat(builtEnrollment.getStudentStatus()).isEqualTo(studentStatus);
    }
}