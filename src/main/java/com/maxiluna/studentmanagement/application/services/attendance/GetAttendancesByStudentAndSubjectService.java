package com.maxiluna.studentmanagement.application.services.attendance;

import com.maxiluna.studentmanagement.domain.models.Attendance;
import com.maxiluna.studentmanagement.domain.usecases.attendance.GetAttendancesByStudentAndSubjectUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.AttendanceJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.ClassRecordJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaAttendanceRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaClassRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAttendancesByStudentAndSubjectService implements GetAttendancesByStudentAndSubjectUseCase {
    @Autowired
    private JpaAttendanceRepository attendanceRepository;

    @Autowired
    private JpaClassRecordRepository classRecordRepository;


    @Override
    public List<Attendance> execute(Long studentId, Long subjectId) {
        List<ClassRecordJpa> classRecordJpaList = classRecordRepository.findBySubjectId(subjectId);

        List<AttendanceJpa> allAttendances = new ArrayList<>();

        for (ClassRecordJpa classRecordJpa : classRecordJpaList) {
            List<AttendanceJpa> attendancesOfClass = classRecordJpa.getAttendances();

            List<AttendanceJpa> attendancesOfStudentInClass = attendancesOfClass.stream()
                    .filter(attendance -> attendance.getStudent().getId().equals(studentId))
                    .toList();

            allAttendances.addAll(attendancesOfStudentInClass);
        }

        return allAttendances.stream()
                .map(AttendanceJpa::toAttendance)
                .collect(Collectors.toList());
    }
}
