package com.maxiluna.studentmanagement.application.services.classRecord;

import com.maxiluna.studentmanagement.domain.exceptions.ClassRecordNotFoundException;
import com.maxiluna.studentmanagement.domain.models.ClassRecord;
import com.maxiluna.studentmanagement.domain.usecases.classRecord.GetClassRecordUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.ClassRecordJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaClassRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetClassRecordService implements GetClassRecordUseCase {
    @Autowired
    private JpaClassRecordRepository classRecordRepository;

    @Override
    public ClassRecord execute(Long classRecordId) {
        if (classRecordId <= 0) {
            throw new IllegalArgumentException("Invalid class record ID: " + classRecordId);
        }
        ClassRecordJpa classRecordJpa = classRecordRepository.findById(classRecordId)
                .orElseThrow(() -> new ClassRecordNotFoundException("Class record not found with ID: " + classRecordId));

        return classRecordJpa.toClassRecord();
    }
}
