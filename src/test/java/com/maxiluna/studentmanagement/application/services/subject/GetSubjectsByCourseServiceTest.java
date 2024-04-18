package com.maxiluna.studentmanagement.application.services.subject;

import com.maxiluna.studentmanagement.domain.models.Subject;
import com.maxiluna.studentmanagement.infrastructure.entities.CourseJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.UserJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaSubjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetSubjectsByCourseServiceTest {
    @Mock
    private JpaSubjectRepository subjectRepository;

    @InjectMocks
    private GetSubjectsByCourseService getSubjectsByCourseService;

    @Test
    @DisplayName("List subjects by course - Successful")
    public void listSubjectsByCourse_Successful() {
        // Arrange
        Long courseId = 1L;

        List<SubjectJpa> subjectsJpaList = createSubjectJpaList();
        List<Subject> expectedSubjects = subjectsJpaList.stream()
                .map(SubjectJpa::toSubject)
                .collect(Collectors.toList());

        when(subjectRepository.findByCourseId(courseId)).thenReturn(subjectsJpaList);

        // Act
        List<Subject> actualSubjects = getSubjectsByCourseService.execute(courseId);

        // Assert
        assertThat(actualSubjects).isNotNull();
        assertThat(actualSubjects)
                .hasSize(expectedSubjects.size())
                .containsAll(expectedSubjects);
    }

    @Test
    @DisplayName("Get subjects with invalid course id - Throws exception")
    public void getSubjectsWthInvalidCourseId_ThrowsException() {
        // Arrange
        Long invalidId = 0L;

        // Act & Assert
        assertThatThrownBy(() -> getSubjectsByCourseService.execute(invalidId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid course ID: " + invalidId);

        // Verify
        verifyNoInteractions(subjectRepository);
    }

    private List<SubjectJpa> createSubjectJpaList() {
        CourseJpa course = CourseJpa.builder()
                .id(1L)
                .name("Engineering")
                .institutionName("Example University")
                .durationInYears(5.0)
                .build();

        UserJpa teacher = UserJpa.builder()
                .id(1L)
                .email("john.doe@example.com")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1995,5,5))
                .role("TEACHER")
                .build();

        List<SubjectJpa> subjects = new ArrayList<>();

        SubjectJpa subjectJpa1 = SubjectJpa.builder()
                .id(1L)
                .name("Algebra")
                .academicYear(2022)
                .course(course)
                .teacher(teacher)
                .build();
        SubjectJpa subjectJpa2 = SubjectJpa.builder()
                .id(2L)
                .name("Math")
                .academicYear(2023)
                .course(course)
                .teacher(teacher)
                .build();

        subjects.add(subjectJpa1);
        subjects.add(subjectJpa2);

        return subjects;
    }
}