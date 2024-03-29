package com.maxiluna.studentmanagement.infrastructure.persistence;

import com.maxiluna.studentmanagement.infrastructure.entities.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class JpaGradeRepositoryIntegrationTest {
    @Autowired
    private JpaGradeRepository gradeRepository;

    @Autowired
    private JpaSubjectRepository subjectRepository;

    @Autowired
    private JpaStudentRepository studentRepository;

    @Autowired
    private JpaClassRecordRepository classRecordRepository;

    @Test
    @DisplayName("Save grade - Successful")
    public void saveGrade_Successful() {
        // Arrange
        GradeJpa grade = createGradeJpa();

        // Act
        GradeJpa savedGrade = gradeRepository.save(grade);

        // Assert
        assertThat(savedGrade.getId()).isNotNull();
    }

    @Test
    @DisplayName("Find grade by id - Successful")
    public void findGradeById_Successful() {
        // Arrange
        GradeJpa grade = createGradeJpa();
        GradeJpa savedGrade = gradeRepository.save(grade);

        // Act
        Optional<GradeJpa> foundGradeOptional = gradeRepository.findById(savedGrade.getId());

        // Assert
        assertThat(foundGradeOptional).isPresent();
        assertThat(foundGradeOptional.get().getId()).isEqualTo(savedGrade.getId());
    }

    @Test
    @DisplayName("Find grade by id - Not found")
    public void findGradeById_NotFound() {
        // Arrange
        Long nonExistentId = 999L;

        // Act
        Optional<GradeJpa> foundGradeOptional = gradeRepository.findById(nonExistentId);

        // Assert
        assertThat(foundGradeOptional).isEmpty();
    }

    @Test
    @DisplayName("Find all grades - Successful")
    public void findAllGrades_Successful() {
        // Arrange
        GradeJpa grade1 = createGradeJpa();
        GradeJpa grade2 = GradeJpa.builder()
                .recordDate(LocalDate.now())
                .description("Second partial exam")
                .score(70.0)
                .subject(grade1.getSubject())
                .student(grade1.getStudent())
                .classRecord(grade1.getClassRecord())
                .build();

        gradeRepository.save(grade1);
        gradeRepository.save(grade2);

        // Act
        List<GradeJpa> foundGrades = gradeRepository.findAll();

        // Assert
        assertThat(foundGrades)
                .isNotNull()
                .hasSize(2);
        assertThat(foundGrades).contains(grade1, grade2);
    }

    @Test
    @DisplayName("Delete grade - Successful")
    public void deleteGrade_Successful() {
        // Arrange
        GradeJpa grade = createGradeJpa();
        GradeJpa savedGrade = gradeRepository.save(grade);

        // Act
        gradeRepository.delete(savedGrade);

        // Assert
        Optional<GradeJpa> deletedGradeOptional = gradeRepository.findById(savedGrade.getId());
        assertThat(deletedGradeOptional).isEmpty();
    }

    @Test
    @DisplayName("Find grades by subject id - Successful")
    public void findGradesBySubjectId_Successful() {
        // Arrange
        GradeJpa grade1 = createGradeJpa();
        GradeJpa grade2 = GradeJpa.builder()
                .recordDate(LocalDate.now())
                .description("Second partial exam")
                .score(70.0)
                .subject(grade1.getSubject())
                .student(grade1.getStudent())
                .classRecord(grade1.getClassRecord())
                .build();

        Long subjectId = grade1.getSubject().getId();

        gradeRepository.save(grade1);
        gradeRepository.save(grade2);

        // Act
        List<GradeJpa> foundGrades = gradeRepository.findBySubjectId(subjectId);

        // Assert
        assertThat(foundGrades)
                .isNotNull()
                .hasSize(2);
        assertThat(foundGrades).contains(grade1, grade2);
    }

    @Test
    @DisplayName("Find grades by student id - Successful")
    public void findGradesByStudentId_Successful() {
        // Arrange
        GradeJpa grade1 = createGradeJpa();
        GradeJpa grade2 = GradeJpa.builder()
                .recordDate(LocalDate.now())
                .description("Second partial exam")
                .score(70.0)
                .subject(grade1.getSubject())
                .student(grade1.getStudent())
                .classRecord(grade1.getClassRecord())
                .build();

        Long studentId = grade1.getStudent().getId();

        gradeRepository.save(grade1);
        gradeRepository.save(grade2);

        // Act
        List<GradeJpa> foundGrades = gradeRepository.findByStudentId(studentId);

        // Assert
        assertThat(foundGrades)
                .isNotNull()
                .hasSize(2);
        assertThat(foundGrades).contains(grade1, grade2);
    }

    @Test
    @DisplayName("Find grades by classRecord id - Successful")
    public void findGradesByClassRecordId_Successful() {
        // Arrange
        GradeJpa grade1 = createGradeJpa();
        GradeJpa grade2 = GradeJpa.builder()
                .recordDate(LocalDate.now())
                .description("Second partial exam")
                .score(70.0)
                .subject(grade1.getSubject())
                .student(grade1.getStudent())
                .classRecord(grade1.getClassRecord())
                .build();

        Long classRecordId = grade1.getClassRecord().getId();

        gradeRepository.save(grade1);
        gradeRepository.save(grade2);

        // Act
        List<GradeJpa> foundGrades = gradeRepository.findByClassRecordId(classRecordId);

        // Assert
        assertThat(foundGrades)
                .isNotNull()
                .hasSize(2);
        assertThat(foundGrades).contains(grade1, grade2);
    }

    private GradeJpa createGradeJpa() {
        SubjectJpa subject = SubjectJpa.builder()
                .name("Mathematics")
                .academicYear(2022)
                .build();
        SubjectJpa savedSubject = subjectRepository.save(subject);

        StudentJpa student = StudentJpa.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .birthDate(LocalDate.of(2000, 2, 2))
                .dni("12345678")
                .city("Any City")
                .admissionYear(2022)
                .build();
        StudentJpa savedStudent = studentRepository.save(student);

        ClassRecordJpa classRecord = ClassRecordJpa.builder()
                .topic("Derivatives")
                .activities("Problem resolution")
                .date(LocalDate.now())
                .subject(savedSubject)
                .build();
        ClassRecordJpa savedClassRecord = classRecordRepository.save(classRecord);

        return GradeJpa.builder()
                .recordDate(LocalDate.now())
                .description("First partial exam")
                .score(90.0)
                .subject(savedSubject)
                .student(savedStudent)
                .classRecord(savedClassRecord)
                .build();
    }
}
