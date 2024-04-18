package com.maxiluna.studentmanagement.application.services.subject;

import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.exceptions.SubjectNotFoundException;
import com.maxiluna.studentmanagement.domain.usecases.subject.DeleteSubjectUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteSubjectService implements DeleteSubjectUseCase {
    @Autowired
    private JpaSubjectRepository subjectRepository;

    @Override
    @Transactional
    public void execute(Long subjectId) {
        if (subjectId == null || subjectId <= 0) {
            throw new IllegalArgumentException("Invalid subject ID: " + subjectId);
        }

        try {
            SubjectJpa subjectJpa = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new SubjectNotFoundException("Subject not found with ID: " + subjectId));

            subjectRepository.delete(subjectJpa);
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error deleting subject information: " + ex.getMessage());
        }
    }
}
