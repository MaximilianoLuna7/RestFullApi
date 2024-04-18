package com.maxiluna.studentmanagement.application.services.student;

import com.maxiluna.studentmanagement.domain.exceptions.SubjectNotFoundException;
import com.maxiluna.studentmanagement.domain.models.Student;
import com.maxiluna.studentmanagement.domain.models.StudentStatus;
import com.maxiluna.studentmanagement.domain.usecases.student.CreateStudentUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaStudentRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateStudentService implements CreateStudentUseCase {
    @Autowired
    private JpaSubjectRepository subjectRepository;

    @Autowired
    private JpaStudentRepository studentRepository;

    @Override
    public void execute(Student studentToCreate, Long subjectId, StudentStatus status) {
        SubjectJpa subjectJpa = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found with ID: " + subjectId));



    }
}
