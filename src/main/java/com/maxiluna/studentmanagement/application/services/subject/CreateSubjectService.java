package com.maxiluna.studentmanagement.application.services.subject;

import com.maxiluna.studentmanagement.domain.exceptions.CourseNotFoundException;
import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.exceptions.UnauthorizedAccessException;
import com.maxiluna.studentmanagement.domain.exceptions.UserNotFoundException;
import com.maxiluna.studentmanagement.domain.models.Subject;
import com.maxiluna.studentmanagement.domain.models.UserRole;
import com.maxiluna.studentmanagement.domain.usecases.subject.CreateSubjectUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.CourseJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.UserJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaCourseRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaSubjectRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class CreateSubjectService implements CreateSubjectUseCase {
    @Autowired
    private JpaSubjectRepository subjectRepository;

    @Autowired
    private JpaCourseRepository courseRepository;

    @Autowired
    private JpaUserRepository userRepository;

    @Override
    @Transactional
    public void execute(Subject subjectToCreate, Long courseId, Long teacherId) {
        try {
            // Extract the course and teacher from the database using their id.
            CourseJpa courseJpa = courseRepository.findById(courseId)
                    .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseId));
            UserJpa teacherJpa = userRepository.findById(teacherId)
                    .orElseThrow(() -> new UserNotFoundException("Teacher not found with ID: " + teacherId));

            // Verify that the user has a teacher role
            if (!isTeacher(teacherJpa)) {
                throw new UnauthorizedAccessException("Only teachers can create subjects");
            }

            // Create subjectJpa from subjectToCreate and set the course and teacher
            SubjectJpa subjectJpa = SubjectJpa.fromSubject(subjectToCreate);
            subjectJpa.setCourse(courseJpa);
            subjectJpa.setTeacher(teacherJpa);

            // If the course does not yet have subjects, assign a list and then add the subject
            if (courseJpa.getSubjects() == null) {
                courseJpa.setSubjects(new ArrayList<>());
            }
            courseJpa.getSubjects().add(subjectJpa);

            //If the teacher does not yet have subjects, assign a list and then add the subject
            if (teacherJpa.getSubjects() == null) {
                teacherJpa.setSubjects(new ArrayList<>());
            }
            teacherJpa.getSubjects().add(subjectJpa);

            // Save the new subject
            subjectRepository.save(subjectJpa);

            // Save the other updated entities
            courseRepository.save(courseJpa);
            userRepository.save(teacherJpa);
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error creating subject: " + ex.getMessage());
        }
    }

    private boolean isTeacher(UserJpa user) {
        return UserRole.TEACHER.equals(UserRole.valueOf(user.getRole()));
    }
}
