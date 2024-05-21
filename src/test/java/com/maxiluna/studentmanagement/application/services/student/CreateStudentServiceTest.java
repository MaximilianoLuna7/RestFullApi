package com.maxiluna.studentmanagement.application.services.student;

import com.maxiluna.studentmanagement.domain.usecases.enrollment.CreateEnrollmentUseCase;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaEnrollmentRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaStudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateStudentServiceTest {
    @Mock
    private JpaEnrollmentRepository enrollmentRepository;

    @Mock
    private JpaStudentRepository studentRepository;

    @Mock
    private CreateEnrollmentUseCase createEnrollment;

    @InjectMocks
    private CreateStudentService createStudentService;
}
