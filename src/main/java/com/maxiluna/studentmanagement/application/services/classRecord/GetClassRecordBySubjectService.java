package com.maxiluna.studentmanagement.application.services.classRecord;

import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.models.ClassRecord;
import com.maxiluna.studentmanagement.domain.usecases.classRecord.GetClassRecordsBySubjectUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.ClassRecordJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaClassRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetClassRecordBySubjectService implements GetClassRecordsBySubjectUseCase {
    @Autowired
    private JpaClassRecordRepository classRecordRepository;

    @Override
    public List<ClassRecord> execute(Long subjectId) {
        if (subjectId <= 0) {
            throw new IllegalArgumentException("Invalid subject ID: " + subjectId);
        }

        try {
            List<ClassRecordJpa> classRecordJpaList = classRecordRepository.findBySubjectId(subjectId);

            return classRecordJpaList.stream()
                    .map(ClassRecordJpa::toClassRecord)
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error listing class records: " + ex.getMessage());
        }
    }
}
