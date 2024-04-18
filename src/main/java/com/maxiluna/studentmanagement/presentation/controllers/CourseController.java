package com.maxiluna.studentmanagement.presentation.controllers;

import com.maxiluna.studentmanagement.domain.models.Course;
import com.maxiluna.studentmanagement.domain.usecases.course.*;
import com.maxiluna.studentmanagement.presentation.dtos.course.CourseResponseDto;
import com.maxiluna.studentmanagement.presentation.dtos.course.CreateCourseDto;
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
    public ResponseEntity<Void> createCourse(@RequestBody @Valid CreateCourseDto courseToCreate) {
        Course course = courseToCreate.toCourse();

        createCourseUseCase.execute(course);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponseDto> getCourse(@PathVariable Long courseId) {
        Course course = getCourseDataUseCase.execute(courseId);

        return ResponseEntity.ok(CourseResponseDto.fromCourse(course));
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Void> updateCourse(@PathVariable Long courseId, @RequestBody @Valid CreateCourseDto updatedCreateCourseDto) {
        Course updatedCourse = updatedCreateCourseDto.toCourse();
        updateCourseDataUseCase.execute(courseId, updatedCourse);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        deleteCourseUseCase.execute(courseId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> listCourses() {
        List<Course> coursesList = listCoursesUseCase.execute();

        List<CourseResponseDto> coursesListDto = coursesList.stream()
                .map(CourseResponseDto::fromCourse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(coursesListDto);
    }
}
