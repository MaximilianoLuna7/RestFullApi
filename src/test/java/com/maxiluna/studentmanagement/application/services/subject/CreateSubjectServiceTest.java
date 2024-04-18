package com.maxiluna.studentmanagement.application.services.subject;

import com.maxiluna.studentmanagement.domain.models.Subject;
import com.maxiluna.studentmanagement.infrastructure.entities.CourseJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.UserJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaCourseRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaSubjectRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateSubjectServiceTest {
    @Mock
    private JpaSubjectRepository subjectRepository;

    @Mock
    private JpaCourseRepository courseRepository;

    @Mock
    private JpaUserRepository userRepository;

    @InjectMocks
    private CreateSubjectService createSubjectService;

    @Test
    @DisplayName("Create subject - Successful")
    public void createSubject_Successful() {
        // Arrange
        Long teacherId = 1L;
        Long courseId = 1L;

        UserJpa teacherJpa = UserJpa.builder()
                .id(teacherId)
                .email("john.doe@example.com")
                .password("EncodePass123")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1993, 2,11))
                .role("TEACHER")
                .build();

        CourseJpa courseJpa = CourseJpa.builder()
                .id(courseId)
                .name("Engineering")
                .institutionName("University")
                .durationInYears(5d)
                .build();

        Subject subject = new Subject();
        subject.setName("Algebra");
        subject.setAcademicYear(2022);

        SubjectJpa subjectJpa = SubjectJpa.fromSubject(subject);
        subjectJpa.setCourse(courseJpa);
        subjectJpa.setTeacher(teacherJpa);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseJpa));
        when(userRepository.findById(teacherId)).thenReturn(Optional.of(teacherJpa));

        // Act
        createSubjectService.execute(subject, courseId, teacherId);
        System.out.println(courseJpa);

        // Verify
        verify(subjectRepository, times(1)).save(subjectJpa);
        verify(courseRepository, times(1)).findById(courseId);
        verify(userRepository, times(1)).findById(teacherId);
    }
}