package com.maxiluna.studentmanagement.infrastructure.persistence;

import com.maxiluna.studentmanagement.infrastructure.entities.AttendanceJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.ClassRecordJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.StudentJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class JpaAttendanceRepositoryIntegrationTest {
    @Autowired
    private JpaAttendanceRepository attendanceRepository;

    @Autowired
    private JpaSubjectRepository subjectRepository;

    @Autowired
    private JpaStudentRepository studentRepository;

    @Autowired
    private JpaClassRecordRepository classRecordRepository;

    @Test
    @DisplayName("Save attendance - Successful")
    public void saveAttendance_Successful() {
        // Arrange
        AttendanceJpa attendance = createAttendanceJpa();

        // Act
        AttendanceJpa savedAttendance = attendanceRepository.save(attendance);

        // Assert
        assertThat(savedAttendance.getId()).isNotNull();
    }

    @Test
    @DisplayName("Find attendance by id - Successful")
    public void findAttendanceById_Successful() {
        // Arrange
        AttendanceJpa attendance = createAttendanceJpa();
        AttendanceJpa savedAttendance = attendanceRepository.save(attendance);

        // Act
        Optional<AttendanceJpa> foundAttendanceOptional = attendanceRepository.findById(savedAttendance.getId());

        // Assert
        assertThat(foundAttendanceOptional).isPresent();
        assertThat(foundAttendanceOptional.get().getId()).isEqualTo(savedAttendance.getId());
    }

    @Test
    @DisplayName("Find attendance by id - Not found")
    public void findAttendanceById_NotFound() {
        // Arrange
        Long nonExistentId = 999L;

        // Act
        Optional<AttendanceJpa> foundAttendanceOptional = attendanceRepository.findById(nonExistentId);

        // Assert
        assertThat(foundAttendanceOptional).isEmpty();
    }

    @Test
    @DisplayName("Find all attendances - Successful")
    public void findAllAttendances_Successful() {
        // Arrange
        AttendanceJpa attendance1 = createAttendanceJpa();
        AttendanceJpa attendance2 = AttendanceJpa.builder()
                .dateRecord(LocalDate.now())
                .student(attendance1.getStudent())
                .subject(attendance1.getSubject())
                .classRecord(attendance1.getClassRecord())
                .attendanceStatus("ABSENT")
                .build();

        attendanceRepository.save(attendance1);
        attendanceRepository.save(attendance2);

        // Act
        List<AttendanceJpa> foundAttendances = attendanceRepository.findAll();

        // Assert
        assertThat(foundAttendances)
                .isNotNull()
                .hasSize(2);
        assertThat(foundAttendances).contains(attendance1, attendance2);
    }

    @Test
    @DisplayName("Delete attendance - Successful")
    public void deleteAttendance_Successful() {
        // Arrange
        AttendanceJpa attendance = createAttendanceJpa();
        AttendanceJpa savedAttendance = attendanceRepository.save(attendance);

        // Act
        attendanceRepository.delete(savedAttendance);

        // Assert
        Optional<AttendanceJpa> deletedAttendanceOptional = attendanceRepository.findById(savedAttendance.getId());
        assertThat(deletedAttendanceOptional).isEmpty();
    }

    @Test
    @DisplayName("Find attendances by student id - Successful")
    public void findAttendancesByStudentId_Successful() {
        // Arrange
        AttendanceJpa attendance1 = createAttendanceJpa();
        AttendanceJpa attendance2 = AttendanceJpa.builder()
                .dateRecord(LocalDate.now())
                .student(attendance1.getStudent())
                .subject(attendance1.getSubject())
                .classRecord(attendance1.getClassRecord())
                .attendanceStatus("ABSENT")
                .build();

        Long studentId = attendance1.getStudent().getId();

        attendanceRepository.save(attendance1);
        attendanceRepository.save(attendance2);

        // Act
        List<AttendanceJpa> foundAttendances = attendanceRepository.findByStudentId(studentId);

        // Assert
        assertThat(foundAttendances)
                .isNotNull()
                .hasSize(2);
        assertThat(foundAttendances).contains(attendance1, attendance2);
    }

    @Test
    @DisplayName("Find attendances by subject id - Successful")
    public void findAttendancesBySubjectId_Successful() {
        // Arrange
        AttendanceJpa attendance1 = createAttendanceJpa();
        AttendanceJpa attendance2 = AttendanceJpa.builder()
                .dateRecord(LocalDate.now())
                .student(attendance1.getStudent())
                .subject(attendance1.getSubject())
                .classRecord(attendance1.getClassRecord())
                .attendanceStatus("ABSENT")
                .build();

        Long subjectId = attendance1.getSubject().getId();

        attendanceRepository.save(attendance1);
        attendanceRepository.save(attendance2);

        // Act
        List<AttendanceJpa> foundAttendances = attendanceRepository.findBySubjectId(subjectId);

        // Assert
        assertThat(foundAttendances)
                .isNotNull()
                .hasSize(2);
        assertThat(foundAttendances).contains(attendance1, attendance2);
    }

    @Test
    @DisplayName("Find attendances by classRecord id - Successful")
    public void findAttendancesByClassRecordId_Successful() {
        // Arrange
        AttendanceJpa attendance1 = createAttendanceJpa();
        AttendanceJpa attendance2 = AttendanceJpa.builder()
                .dateRecord(LocalDate.now())
                .student(attendance1.getStudent())
                .subject(attendance1.getSubject())
                .classRecord(attendance1.getClassRecord())
                .attendanceStatus("ABSENT")
                .build();

        Long classRecordId = attendance1.getClassRecord().getId();

        attendanceRepository.save(attendance1);
        attendanceRepository.save(attendance2);

        // Act
        List<AttendanceJpa> foundAttendances = attendanceRepository.findByClassRecordId(classRecordId);

        // Assert
        assertThat(foundAttendances)
                .isNotNull()
                .hasSize(2);
        assertThat(foundAttendances).contains(attendance1, attendance2);
    }

    private AttendanceJpa createAttendanceJpa() {
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

        return AttendanceJpa.builder()
                .dateRecord(LocalDate.now())
                .student(savedStudent)
                .subject(savedSubject)
                .classRecord(savedClassRecord)
                .attendanceStatus("ATTEND")
                .build();
    }
}
