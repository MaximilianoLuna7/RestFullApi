package com.maxiluna.studentmanagement.application.services.subject;

import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.models.Subject;
import com.maxiluna.studentmanagement.domain.usecases.subject.ListSubjectsUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.CourseJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListSubjectsService implements ListSubjectsUseCase {
    @Autowired
    private JpaSubjectRepository subjectRepository;

    @Override
    public List<Subject> execute() {
        try {
            List<SubjectJpa> subjectJpaList = subjectRepository.findAll();

            return subjectJpaList.stream()
                    .map(SubjectJpa::toSubject)
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error listing subjects: " + ex.getMessage());
        }
    }
}
