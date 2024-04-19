package com.maxiluna.studentmanagement.application.services.classRecord;

import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.exceptions.SubjectNotFoundException;
import com.maxiluna.studentmanagement.domain.models.ClassRecord;
import com.maxiluna.studentmanagement.domain.usecases.classRecord.CreateClassRecordUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.ClassRecordJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaClassRecordRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateClassRecordService implements CreateClassRecordUseCase {
    @Autowired
    private JpaClassRecordRepository classRecordRepository;

    @Autowired
    private JpaSubjectRepository subjectRepository;

    @Override
    @Transactional
    public void execute(ClassRecord classRecordToCreate, Long subjectId) {
        try {
            SubjectJpa subjectJpa = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new SubjectNotFoundException("Subject not found with ID: " + subjectId));

            ClassRecordJpa classRecordJpa = ClassRecordJpa.fromClassRecord(classRecordToCreate);
            classRecordJpa.setSubject(subjectJpa);

            classRecordRepository.save(classRecordJpa);
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error creating class record: " + ex.getMessage());
        }
    }
}
