package com.maxiluna.studentmanagement.application.services.grade;

import com.maxiluna.studentmanagement.domain.models.Grade;
import com.maxiluna.studentmanagement.domain.usecases.grade.GetGradesByStudentAndSubjectUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.ClassRecordJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.GradeJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaClassRecordRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetGradesByStudentAndSubjectService implements GetGradesByStudentAndSubjectUseCase {
    @Autowired
    private JpaGradeRepository gradeRepository;

    @Autowired
    private JpaClassRecordRepository classRecordRepository;

    @Override
    public List<Grade> execute(Long studentId, Long subjectId) {
        List<ClassRecordJpa> classRecordJpaList = classRecordRepository.findBySubjectId(subjectId);

        List<GradeJpa> allGrades = new ArrayList<>();

        for (ClassRecordJpa classRecordJpa : classRecordJpaList) {
            List<GradeJpa> gradesOfClass = classRecordJpa.getGrades();

            List<GradeJpa> gradesOfStudentInClass = gradesOfClass.stream()
                    .filter(grade -> grade.getStudent().getId().equals(studentId))
                    .toList();

            allGrades.addAll(gradesOfStudentInClass);
        }

        return allGrades.stream()
                .map(GradeJpa::toGrade)
                .collect(Collectors.toList());
    }
}
