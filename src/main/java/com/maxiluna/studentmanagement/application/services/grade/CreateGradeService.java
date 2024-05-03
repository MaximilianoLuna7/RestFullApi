package com.maxiluna.studentmanagement.application.services.grade;

import com.maxiluna.studentmanagement.domain.exceptions.ClassRecordNotFoundException;
import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.exceptions.StudentNotFoundException;
import com.maxiluna.studentmanagement.domain.models.Grade;
import com.maxiluna.studentmanagement.domain.usecases.grade.CreateGradeUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.ClassRecordJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.GradeJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.StudentJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaClassRecordRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaGradeRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class CreateGradeService implements CreateGradeUseCase {
    @Autowired
    private JpaGradeRepository gradeRepository;

    @Autowired
    private JpaStudentRepository studentRepository;

    @Autowired
    private JpaClassRecordRepository classRecordRepository;

    @Override
    @Transactional
    public void execute(Grade gradeToCreate, Long studentId, Long classRecordId) {
        try {
            StudentJpa studentJpa = studentRepository.findById(studentId)
                    .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));
            ClassRecordJpa classRecordJpa = classRecordRepository.findById(classRecordId)
                    .orElseThrow(() -> new ClassRecordNotFoundException("Class record not found with ID: " + classRecordId));

            SubjectJpa subjectJpa = classRecordJpa.getSubject();

            GradeJpa gradeJpa = GradeJpa.fromGrade(gradeToCreate);
            gradeJpa.setStudent(studentJpa);
            gradeJpa.setClassRecord(classRecordJpa);

            gradeRepository.save(gradeJpa);
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error creating grade: " + ex.getMessage());
        }
    }
}
