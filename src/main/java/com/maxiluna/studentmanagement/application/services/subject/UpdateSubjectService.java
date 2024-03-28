package com.maxiluna.studentmanagement.application.services.subject;

import com.maxiluna.studentmanagement.domain.exceptions.CourseNotFoundException;
import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.exceptions.SubjectNotFoundException;
import com.maxiluna.studentmanagement.domain.models.Subject;
import com.maxiluna.studentmanagement.domain.usecases.subject.UpdateSubjectUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.CourseJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateSubjectService implements UpdateSubjectUseCase {
    @Autowired
    private JpaSubjectRepository subjectRepository;

    @Override
    @Transactional
    public void updateSubject(Long subjectId, Subject updatedSubject) {
        if (subjectId <= 0) {
            throw new IllegalArgumentException("Invalid subject ID: " + subjectId);
        }

        try {
            SubjectJpa subjectJpa = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new SubjectNotFoundException("Subject not found with ID: " + subjectId));

            subjectJpa.setName(updatedSubject.getName());
            subjectJpa.setAcademicYear(updatedSubject.getAcademicYear());

            subjectRepository.save(subjectJpa);
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error updating subject data: " + ex.getMessage());
        }
    }
}
