package com.maxiluna.studentmanagement.presentation.controllers;

import com.maxiluna.studentmanagement.domain.models.Course;
import com.maxiluna.studentmanagement.domain.usecases.course.*;
import com.maxiluna.studentmanagement.presentation.dtos.course.CourseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CreateCourseUseCase createCourseUseCase;

    @Autowired
    private GetCourseDataUseCase getCourseDataUseCase;

    @Autowired
    private UpdateCourseDataUseCase updateCourseDataUseCase;

    @Autowired
    private DeleteCourseUseCase deleteCourseUseCase;

    @Autowired
    private ListCoursesUseCase listCoursesUseCase;

    @PostMapping("/create")
    public ResponseEntity<Void> createCourse(@RequestBody @Valid CourseDto courseToCreate) {
        Course course = courseToCreate.toCourse();

        createCourseUseCase.createCourse(course);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable Long courseId) {
        Course course = getCourseDataUseCase.getCourseData(courseId);

        return ResponseEntity.ok(CourseDto.fromCourse(course));
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Void> updateCourse(@PathVariable Long courseId, @RequestBody @Valid CourseDto updatedCourseDto) {
        Course updatedCourse = updatedCourseDto.toCourse();
        updateCourseDataUseCase.updateCourse(courseId, updatedCourse);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        deleteCourseUseCase.deleteCourse(courseId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> listCourses() {
        List<Course> coursesList = listCoursesUseCase.listCourses();

        List<CourseDto> coursesListDto = coursesList.stream()
                .map(CourseDto::fromCourse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(coursesListDto);
    }
}
