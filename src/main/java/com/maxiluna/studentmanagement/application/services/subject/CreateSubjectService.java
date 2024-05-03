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
            CourseJpa courseJpa = courseRepository.findById(courseId)
                    .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseId));
            UserJpa teacherJpa = userRepository.findById(teacherId)
                    .orElseThrow(() -> new UserNotFoundException("Teacher not found with ID: " + teacherId));

            if (!isTeacher(teacherJpa)) {
                throw new UnauthorizedAccessException("Only teachers can create subjects");
            }

            SubjectJpa subjectJpa = SubjectJpa.fromSubject(subjectToCreate);
            subjectJpa.setCourse(courseJpa);
            subjectJpa.setTeacher(teacherJpa);

            subjectRepository.save(subjectJpa);
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error creating subject: " + ex.getMessage());
        }
    }

    private boolean isTeacher(UserJpa user) {
        return UserRole.TEACHER.equals(UserRole.valueOf(user.getRole()));
    }
}
