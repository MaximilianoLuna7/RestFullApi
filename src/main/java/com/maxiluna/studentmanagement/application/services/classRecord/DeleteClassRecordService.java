package com.maxiluna.studentmanagement.application.services.classRecord;

import com.maxiluna.studentmanagement.domain.exceptions.ClassRecordNotFoundException;
import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.usecases.classRecord.DeleteClassRecordUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.ClassRecordJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaClassRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteClassRecordService implements DeleteClassRecordUseCase {
    @Autowired
    private JpaClassRecordRepository classRecordRepository;

    @Override
    @Transactional
    public void execute(Long classRecordId) {
        if (classRecordId <= 0) {
            throw new IllegalArgumentException("Invalid class record ID: " + classRecordId);
        }

        try {
            ClassRecordJpa classRecordJpa = classRecordRepository.findById(classRecordId)
                    .orElseThrow(() -> new ClassRecordNotFoundException("Class record not found with ID: " + classRecordId));

            classRecordRepository.delete(classRecordJpa);
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error deleting class record information: " + classRecordId);
        }
    }
}
