package com.maxiluna.studentmanagement.domain.models;

import com.maxiluna.studentmanagement.infrastructure.entities.AttendanceJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.GradeJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassRecord {
    private Long id;

    @NotBlank(message = "Topic must not be blank")
    @Size(min = 2, max = 255, message = "Topic must not be between 2 and 255")
    private String topic;

    @Size(min = 2, max = 500, message = "Activities must not be between 2 and 500")
    private String activities;

    @NotNull(message = "Date must not be null")
    private LocalDate date;

    @NotNull(message = "Subject must not be null")
    private Subject subject;

    private List<Attendance> attendances = new ArrayList<>();

    private List<Grade> grades = new ArrayList<>();
}
