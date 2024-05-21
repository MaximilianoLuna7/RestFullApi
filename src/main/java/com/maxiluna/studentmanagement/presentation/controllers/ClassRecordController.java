package com.maxiluna.studentmanagement.presentation.controllers;

import com.maxiluna.studentmanagement.domain.models.ClassRecord;
import com.maxiluna.studentmanagement.domain.usecases.classRecord.*;
import com.maxiluna.studentmanagement.presentation.dtos.classRecord.ClassRecordRequestDto;
import com.maxiluna.studentmanagement.presentation.dtos.classRecord.ClassRecordResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/classRecords")
public class ClassRecordController {
    @Autowired
    private CreateClassRecordUseCase createClassRecordUseCase;

    @Autowired
    private GetClassRecordUseCase getClassRecordUseCase;

    @Autowired
    private GetClassRecordsBySubjectUseCase getClassRecordsBySubjectUseCase;

    @Autowired
    private UpdateClassRecordUseCase updateClassRecordUseCase;

    @Autowired
    private DeleteClassRecordUseCase deleteClassRecordUseCase;

    @PostMapping("/create")
    public ResponseEntity<Void> createClassRecord(@RequestBody @Valid ClassRecordRequestDto classRecordToCreate) {
        ClassRecord classRecord = classRecordToCreate.toClassRecord();
        Long subjectId = classRecordToCreate.getSubjectId();

        createClassRecordUseCase.execute(classRecord, subjectId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{classRecordId}")
    public ResponseEntity<ClassRecordResponseDto> getClassRecord(@PathVariable Long classRecordId) {
        ClassRecord classRecord = getClassRecordUseCase.execute(classRecordId);

        return ResponseEntity.ok(ClassRecordResponseDto.fromClassRecord(classRecord));
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<ClassRecordResponseDto>> getClassRecordsBySubject(@PathVariable Long subjectId) {
        List<ClassRecord> classRecordList = getClassRecordsBySubjectUseCase.execute(subjectId);

        List<ClassRecordResponseDto> classRecordListDto = classRecordList.stream()
                .map(ClassRecordResponseDto::fromClassRecord)
                .collect(Collectors.toList());

        return ResponseEntity.ok(classRecordListDto);
    }

    @PutMapping("/{classRecordId}")
    public ResponseEntity<Void> updateClassRecord(@PathVariable Long classRecordId, @RequestBody @Valid ClassRecordRequestDto updatedClassRecordDto) {
        ClassRecord updatedClassRecord = updatedClassRecordDto.toClassRecord();

        updateClassRecordUseCase.execute(classRecordId, updatedClassRecord);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{classRecordId}")
    public ResponseEntity<Void> deleteClassRecord(@PathVariable Long classRecordId) {
        deleteClassRecordUseCase.execute(classRecordId);

        return ResponseEntity.noContent().build();
    }
}
