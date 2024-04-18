package com.maxiluna.studentmanagement.application.services.subject;

import com.maxiluna.studentmanagement.domain.exceptions.SubjectNotFoundException;
import com.maxiluna.studentmanagement.domain.models.Subject;
import com.maxiluna.studentmanagement.domain.usecases.subject.GetSubjectUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetSubjectService implements GetSubjectUseCase {
    @Autowired
    private JpaSubjectRepository subjectRepository;

    @Override
    public Subject execute(Long subjectId) {
        if (subjectId <= 0) {
            throw new IllegalArgumentException("Invalid subject ID: " + subjectId);
        }

        SubjectJpa subjectJpa = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found with ID: " + subjectId));

        return subjectJpa.toSubject();
    }
}
