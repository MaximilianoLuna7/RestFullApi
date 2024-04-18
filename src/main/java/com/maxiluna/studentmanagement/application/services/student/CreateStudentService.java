package com.maxiluna.studentmanagement.application.services.student;

import com.maxiluna.studentmanagement.domain.exceptions.SubjectNotFoundException;
import com.maxiluna.studentmanagement.domain.models.Enrollment;
import com.maxiluna.studentmanagement.domain.models.Student;
import com.maxiluna.studentmanagement.domain.models.StudentStatus;
import com.maxiluna.studentmanagement.domain.usecases.enrollment.CreateEnrollmentUseCase;
import com.maxiluna.studentmanagement.domain.usecases.student.CreateStudentUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.StudentJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaEnrollmentRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaStudentRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateStudentService implements CreateStudentUseCase {
    @Autowired
    private JpaEnrollmentRepository enrollmentRepository;

    @Autowired
    private JpaStudentRepository studentRepository;

    @Autowired
    private CreateEnrollmentUseCase createEnrollment;

    @Override
    @Transactional
    public void execute(Student studentToCreate, Long subjectId, StudentStatus status) {
        StudentJpa studentJpa = StudentJpa.fromStudent(studentToCreate);
        StudentJpa createdStudent = studentRepository.save(studentJpa);

        Long studentId = createdStudent.getId();

        createEnrollment.execute(studentId, subjectId, status);
    }
}
