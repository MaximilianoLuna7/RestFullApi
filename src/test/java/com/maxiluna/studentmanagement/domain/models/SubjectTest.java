package com.maxiluna.studentmanagement.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SubjectTest {

    private final String name = "Math";

    private final Integer academicYear = 2024;

    @Test
    @DisplayName("Create Subject instance")
    public void createSubject_SuccessfulInstantiation() {
        // Arrange & Act
        Subject subject = new Subject();

        // Assert
        assertThat(subject).isNotNull();
    }

    @Test
    @DisplayName("Set and get 'name' property - Successful")
    public void setAndGetName_Successful() {
        // Arrange
        Subject subject = new Subject();

        // Act
        subject.setName(name);

        // Assert
        assertThat(subject.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("Set and get 'institutionName' property - Successful")
    public void setAndGetInstitutionName_Successful() {
        // Arrange
        Subject subject = new Subject();

        // Act
        subject.setAcademicYear(academicYear);

        // Assert
        assertThat(subject.getAcademicYear()).isEqualTo(academicYear);
    }

    @Test
    @DisplayName("Equality test")
    public void equalityTest() {
        // Arrange
        Subject subject = new Subject();
        Subject equalSubject = new Subject();

        // Act
        subject.setName(name);
        subject.setAcademicYear(academicYear);

        equalSubject.setName(name);
        equalSubject.setAcademicYear(academicYear);

        // Assert
        assertThat(subject).isEqualTo(equalSubject);
    }

    @Test
    @DisplayName("Builder test")
    public void builderTest() {
        // Arrange & Act
        Subject builtSubject = Subject.builder()
                .name(name)
                .academicYear(academicYear)
                .build();

        // Assert
        assertThat(builtSubject.getName()).isEqualTo(name);
        assertThat(builtSubject.getAcademicYear()).isEqualTo(academicYear);
    }
}
