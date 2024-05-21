package com.maxiluna.studentmanagement.presentation.dtos.classRecord;

import com.maxiluna.studentmanagement.domain.models.ClassRecord;
import com.maxiluna.studentmanagement.presentation.dtos.subject.SubjectResponseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassRecordResponseDto {
    private Long id;

    @NotBlank(message = "Topic must not be blank")
    @Size(min = 2, max = 255, message = "Topic must not be between 2 and 255")
    private String topic;

    @Size(min = 2, max = 500, message = "Activities must not be between 2 and 500")
    private String activities;

    @NotNull(message = "Date must not be null")
    private LocalDate date;

    private SubjectResponseDto subject;

    public static ClassRecordResponseDto fromClassRecord(ClassRecord classRecord) {
        return ClassRecordResponseDto.builder()
                .id(classRecord.getId())
                .topic(classRecord.getTopic())
                .activities(classRecord.getActivities())
                .date(classRecord.getDate())
                .subject(SubjectResponseDto.fromSubject(classRecord.getSubject()))
                .build();
    }
}
