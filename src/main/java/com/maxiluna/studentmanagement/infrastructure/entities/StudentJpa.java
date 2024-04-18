package com.maxiluna.studentmanagement.infrastructure.entities;

import com.maxiluna.studentmanagement.domain.models.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "students")
public class StudentJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    private String email;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String dni;

    private String city;

    @Column(name = "admission_year")
    private Integer admissionYear;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<EnrollmentJpa> enrollments;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<AttendanceJpa> attendances;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<GradeJpa> grades;

    public static StudentJpa fromStudent(Student student) {
        return StudentJpa.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .birthDate(student.getBirthDate())
                .dni(student.getDni())
                .city(student.getCity())
                .admissionYear(student.getAdmissionYear())
                .build();
    }

    public Student toStudent() {
        return Student.builder()
                .id(this.id)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .birthDate(this.birthDate)
                .dni(this.dni)
                .city(this.city)
                .admissionYear(this.admissionYear)
                .build();
    }
}
