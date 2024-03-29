package com.maxiluna.studentmanagement.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ClassRecordTest {
    private final String topic = "Derivatives";
    private final String activities = "Problem resolution";
    private final LocalDate date = LocalDate.now();
    private Subject subject = Subject.builder()
            .name("Algebra")
            .academicYear(2022)
            .course(new Course())
            .teacher(new User())
            .build();

    @Test
    @DisplayName("Create ClassRecord instance")
    public void createClassRecord_SuccessfulInstantiation() {
        // Arrange & Act
        ClassRecord classRecord = new ClassRecord();

        // Assert
        assertThat(classRecord).isNotNull();
    }

    @Test
    @DisplayName("Set and get 'topic' property - Successful")
    public void setAndGetTopic_Successful() {
        // Arrange
        ClassRecord classRecord = new ClassRecord();

        // Act
        classRecord.setTopic(topic);

        // Assert
        assertThat(classRecord.getTopic()).isEqualTo(topic);
    }

    @Test
    @DisplayName("Set and get 'activities' property - Successful")
    public void setAndGetActivities_Successful() {
        // Arrange
        ClassRecord classRecord = new ClassRecord();

        // Act
        classRecord.setActivities(activities);

        // Assert
        assertThat(classRecord.getActivities()).isEqualTo(activities);
    }

    @Test
    @DisplayName("Set and get 'date' property - Successful")
    public void setAndGetDate_Successful() {
        // Arrange
        ClassRecord classRecord = new ClassRecord();

        // Act
        classRecord.setDate(date);

        // Assert
        assertThat(classRecord.getDate()).isEqualTo(date);
    }

    @Test
    @DisplayName("Set and get 'subject' property - Successful")
    public void setAndGetSubject_Successful() {
        // Arrange
        ClassRecord classRecord = new ClassRecord();

        // Act
        classRecord.setSubject(subject);

        // Assert
        assertThat(classRecord.getSubject()).isEqualTo(subject);
    }

    @Test
    @DisplayName("Equality test")
    public void equalityTest() {
        // Arrange
        ClassRecord classRecord = new ClassRecord();
        ClassRecord equalClassRecord = new ClassRecord();

        // Act
        classRecord.setTopic(topic);
        classRecord.setActivities(activities);
        classRecord.setDate(date);
        classRecord.setSubject(subject);

        equalClassRecord.setTopic(topic);
        equalClassRecord.setActivities(activities);
        equalClassRecord.setDate(date);
        equalClassRecord.setSubject(subject);

        // Assert
        assertThat(classRecord).isEqualTo(equalClassRecord);
    }

    @Test
    @DisplayName("Builder test")
    public void builderTest() {
        // Arrange & Act
        ClassRecord builtClassRecord = ClassRecord.builder()
                .topic(topic)
                .activities(activities)
                .date(date)
                .subject(subject)
                .build();

        // Assert
        assertThat(builtClassRecord.getTopic()).isEqualTo(topic);
        assertThat(builtClassRecord.getActivities()).isEqualTo(activities);
        assertThat(builtClassRecord.getDate()).isEqualTo(date);
        assertThat(builtClassRecord.getSubject()).isEqualTo(subject);
    }
}