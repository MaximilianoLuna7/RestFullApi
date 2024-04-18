package com.maxiluna.studentmanagement.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AttendanceTest {
    private final LocalDate dateRecord = LocalDate.now();
    private final Student student = new Student();
    private final ClassRecord classRecord = new ClassRecord();
    private final AttendanceStatus attendanceStatus = AttendanceStatus.ATTEND;

    @Test
    @DisplayName("Create Attendance instance")
    public void createStudent_SuccessfulInstantiation() {
        // Arrange & Act
        Attendance attendance = new Attendance();

        // Assert
        assertThat(attendance).isNotNull();
    }

    @Test
    @DisplayName("Set and get 'dateRecord' property - Successful")
    public void setAndGetDateRecord_Successful() {
        // Arrange
        Attendance attendance = new Attendance();

        // Act
        attendance.setDateRecord(dateRecord);

        // Assert
        assertThat(attendance.getDateRecord()).isEqualTo(dateRecord);
    }

    @Test
    @DisplayName("Set and get 'student' property - Successful")
    public void setAndGetStudent_Successful() {
        // Arrange
        Attendance attendance = new Attendance();

        // Act
        attendance.setStudent(student);

        // Assert
        assertThat(attendance.getStudent()).isEqualTo(student);
    }

    @Test
    @DisplayName("Set and get 'classRecord' property - Successful")
    public void setAndGetClassRecord_Successful() {
        // Arrange
        Attendance attendance = new Attendance();

        // Act
        attendance.setClassRecord(classRecord);

        // Assert
        assertThat(attendance.getClassRecord()).isEqualTo(classRecord);
    }

    @Test
    @DisplayName("Equality test")
    public void equalityTest() {
        // Arrange
        Attendance attendance = new Attendance();
        Attendance equalAttendance = new Attendance();

        // Act
        attendance.setDateRecord(dateRecord);
        attendance.setStudent(student);
        attendance.setClassRecord(classRecord);
        attendance.setAttendanceStatus(attendanceStatus);

        equalAttendance.setDateRecord(dateRecord);
        equalAttendance.setStudent(student);
        equalAttendance.setClassRecord(classRecord);
        equalAttendance.setAttendanceStatus(attendanceStatus);

        // Assert
        assertThat(attendance).isEqualTo(equalAttendance);
    }

    @Test
    @DisplayName("Builder test")
    public void builderTest() {
        // Arrange & Act
        Attendance builtAttendance = Attendance.builder()
                .dateRecord(dateRecord)
                .student(student)
                .classRecord(classRecord)
                .attendanceStatus(attendanceStatus)
                .build();

        // Assert
        assertThat(builtAttendance.getDateRecord()).isEqualTo(dateRecord);
        assertThat(builtAttendance.getStudent()).isEqualTo(student);
        assertThat(builtAttendance.getClassRecord()).isEqualTo(classRecord);
        assertThat(builtAttendance.getAttendanceStatus()).isEqualTo(attendanceStatus);
    }
}