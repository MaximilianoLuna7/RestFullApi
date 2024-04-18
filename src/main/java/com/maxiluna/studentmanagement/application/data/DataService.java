package com.maxiluna.studentmanagement.application.data;

import com.maxiluna.studentmanagement.infrastructure.entities.CourseJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.UserJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaCourseRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaSubjectRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;

@Service
public class DataService {

    @Autowired
    private JpaUserRepository userRepository;

    @Autowired
    private JpaSubjectRepository subjectRepository;

    @Autowired
    private JpaCourseRepository courseRepository;

    // Método para cargar los datos
    public void loadData() {
        loadUsers();
        loadCourses();
        loadSubjects();
    }

    // Método para cargar usuarios
    private void loadUsers() {
        // Crear instancias de usuarios
        UserJpa admin = UserJpa.builder()
                .email("admin@example.com")
                .password("$2a$10$oymPUjTBqn8nm8bARzQq3eLB0P.zsHCWWzn/9z7i8gglB8IEf3u.m")
                .firstName("Admin")
                .lastName("User")
                .birthDate(LocalDate.of(1990, 1, 1))
                .role("ADMIN")
                .build();

        UserJpa professor1 = UserJpa.builder()
                .email("professor1@example.com")
                .password("$2a$10$gGR4HP9aniTZlKnpa.Jih.GwQWdNoDU2A0h3rWqlZG2Uyagffv5Im")
                .firstName("Professor")
                .lastName("One")
                .birthDate(LocalDate.of(1985, 5, 15))
                .role("TEACHER")
                .build();

        UserJpa professor2 = UserJpa.builder()
                .email("professor2@example.com")
                .password("$2a$10$Xc4kTq0oL9TXD1bIrXyyhOkW7AefgCeNJVUHKksuOMetpImy/CCeS")
                .firstName("Professor")
                .lastName("Two")
                .birthDate(LocalDate.of(1980, 10, 20))
                .role("TEACHER")
                .build();

        // Guardar usuarios en la base de datos
        userRepository.saveAll(Arrays.asList(admin, professor1, professor2));
    }

    // Método para cargar cursos
    private void loadCourses() {
        // Crear instancia de curso
        CourseJpa computerScience = CourseJpa.builder()
                .name("Computer Science")
                .institutionName("University")
                .durationInYears(4.0)
                .build();

        // Guardar curso en la base de datos
        courseRepository.save(computerScience);
    }

    // Método para cargar asignaturas
    private void loadSubjects() {
        // Obtener usuarios
        UserJpa professor1 = userRepository.findByEmail("professor1@example.com").orElse(null);
        UserJpa professor2 = userRepository.findByEmail("professor2@example.com").orElse(null);

        // Obtener curso
        CourseJpa computerScience = courseRepository.findById(1L).orElse(null);

        // Crear instancias de asignaturas
        SubjectJpa mathematics = SubjectJpa.builder()
                .name("Mathematics")
                .academicYear(2024)
                .course(computerScience)
                .teacher(professor1)
                .build();

        SubjectJpa physics = SubjectJpa.builder()
                .name("Physics")
                .academicYear(2024)
                .course(computerScience)
                .teacher(professor1)
                .build();

        SubjectJpa chemistry = SubjectJpa.builder()
                .name("Chemistry")
                .academicYear(2024)
                .course(computerScience)
                .teacher(professor2)
                .build();

        SubjectJpa biology = SubjectJpa.builder()
                .name("Biology")
                .academicYear(2024)
                .course(computerScience)
                .teacher(professor2)
                .build();

        // Guardar asignaturas en la base de datos
        subjectRepository.saveAll(Arrays.asList(mathematics, physics, chemistry, biology));
    }
}

