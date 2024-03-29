package com.maxiluna.studentmanagement.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GradeTest {
    private final LocalDate recordDate = LocalDate.now();
    private final String description = "First partial exam";
    private final Double score = 90.0;
    private final Student student = new Student();
    private final Subject subject = new Subject();
    private final ClassRecord classRecord = new ClassRecord();

    @Test
    @DisplayName("Create Grade instance")
    public void createStudent_SuccessfulInstantiation() {
        // Arrange & Act
        Grade grade = new Grade();

        // Assert
        assertThat(grade).isNotNull();
    }

    @Test
    @DisplayName("Set and get 'recordDate' property - Successful")
    public void setAndGetRecordDate_Successful() {
        // Arrange
        Grade grade = new Grade();

        // Act
        grade.setRecordDate(recordDate);

        // Assert
        assertThat(grade.getRecordDate()).isEqualTo(recordDate);
    }

    @Test
    @DisplayName("Set and get 'description' property - Successful")
    public void setAndGetDescription_Successful() {
        // Arrange
        Grade grade = new Grade();

        // Act
        grade.setDescription(description);

        // Assert
        assertThat(grade.getDescription()).isEqualTo(description);
    }

    @Test
    @DisplayName("Set and get 'score' property - Successful")
    public void setAndGetScore_Successful() {
        // Arrange
        Grade grade = new Grade();

        // Act
        grade.setScore(score);

        // Assert
        assertThat(grade.getScore()).isEqualTo(score);
    }

    @Test
    @DisplayName("Set and get 'subject' property - Successful")
    public void setAndGetSubject_Successful() {
        // Arrange
        Grade grade = new Grade();

        // Act
        grade.setSubject(subject);

        // Assert
        assertThat(grade.getSubject()).isEqualTo(subject);
    }

    @Test
    @DisplayName("Set and get 'student' property - Successful")
    public void setAndGetStudent_Successful() {
        // Arrange
        Grade grade = new Grade();

        // Act
        grade.setStudent(student);

        // Assert
        assertThat(grade.getStudent()).isEqualTo(student);
    }

    @Test
    @DisplayName("Set and get 'classRecord' property - Successful")
    public void setAndGetClassRecord_Successful() {
        // Arrange
        Grade grade = new Grade();

        // Act
        grade.setClassRecord(classRecord);

        // Assert
        assertThat(grade.getClassRecord()).isEqualTo(classRecord);
    }

    @Test
    @DisplayName("Equality test")
    public void equalityTest() {
        // Arrange
        Grade grade = new Grade();
        Grade equalGrade = new Grade();

        // Act
        grade.setRecordDate(recordDate);
        grade.setDescription(description);
        grade.setScore(score);
        grade.setSubject(subject);
        grade.setStudent(student);
        grade.setClassRecord(classRecord);

        equalGrade.setRecordDate(recordDate);
        equalGrade.setDescription(description);
        equalGrade.setScore(score);
        equalGrade.setSubject(subject);
        equalGrade.setStudent(student);
        equalGrade.setClassRecord(classRecord);

        // Assert
        assertThat(grade).isEqualTo(equalGrade);
    }

    @Test
    @DisplayName("Builder test")
    public void builderTest() {
        // Arrange & Act
        Grade builtGrade = Grade.builder()
                .recordDate(recordDate)
                .description(description)
                .score(score)
                .subject(subject)
                .student(student)
                .classRecord(classRecord)
                .build();

        // Assert
        assertThat(builtGrade.getRecordDate()).isEqualTo(recordDate);
        assertThat(builtGrade.getDescription()).isEqualTo(description);
        assertThat(builtGrade.getScore()).isEqualTo(score);
        assertThat(builtGrade.getSubject()).isEqualTo(subject);
        assertThat(builtGrade.getStudent()).isEqualTo(student);
        assertThat(builtGrade.getClassRecord()).isEqualTo(classRecord);
    }
}