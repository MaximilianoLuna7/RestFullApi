package com.maxiluna.studentmanagement.presentation.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxiluna.studentmanagement.config.jwt.JwtAuthenticationFilter;
import com.maxiluna.studentmanagement.domain.exceptions.CourseNotFoundException;
import com.maxiluna.studentmanagement.domain.models.Course;
import com.maxiluna.studentmanagement.domain.usecases.course.*;
import com.maxiluna.studentmanagement.presentation.dtos.course.CourseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    private CreateCourseUseCase createCourseUseCase;

    @MockBean
    private GetCourseDataUseCase getCourseDataUseCase;

    @MockBean
    private UpdateCourseDataUseCase updateCourseDataUseCase;

    @MockBean
    private DeleteCourseUseCase deleteCourseUseCase;

    @MockBean
    private ListCoursesUseCase listCoursesUseCase;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Create course - Successful")
    public void createCourse_Successful() throws Exception {
        // Arrange
        CourseDto courseDto = createCourseDto();
        Course course = courseDto.toCourse();

        // Act & Assert
        mockMvc.perform(post("/api/courses/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(courseDto)))
                .andExpect(status().isNoContent());

        // Verify
        verify(createCourseUseCase, times(1)).createCourse(course);
    }

    @Test
    @DisplayName("Get course data - Successful")
    public void getCourse_Successful() throws Exception {
        // Arrange
        Long courseId = 1L;
        Course course = createCourse();
        CourseDto courseDto = CourseDto.fromCourse(course);

        when(getCourseDataUseCase.getCourseData(courseId)).thenReturn(course);

        // Act & Assert
        mockMvc.perform(get("/api/courses/{courseId}", courseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseDto.getId()))
                .andExpect(jsonPath("$.name").value(courseDto.getName()))
                .andExpect(jsonPath("$.institutionName").value(courseDto.getInstitutionName()))
                .andExpect(jsonPath("$.durationInYears").value(courseDto.getDurationInYears()));

        // Verify
        verify(getCourseDataUseCase, times(1)).getCourseData(courseId);
    }

    @Test
    @DisplayName("Get course data with invalid ID - Throws Exception")
    public void getCourseWithInvalidId_TrowsException() throws Exception {
        // Arrange
        Long invalidCourseId = -1L;
        String expectedErrorMessage = "Invalid course ID: " + invalidCourseId;

        when(getCourseDataUseCase.getCourseData(invalidCourseId))
                .thenThrow(new IllegalArgumentException(expectedErrorMessage));

        // Act & Assert
        mockMvc.perform(get("/api/courses/{courseId}", invalidCourseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage))
                .andExpect(jsonPath("$.path").value("/api/courses/" + invalidCourseId));

        // Verify
        verify(getCourseDataUseCase, times(1)).getCourseData(invalidCourseId);
    }

    @Test
    @DisplayName("Get course data with non extistent ID - Throws Exception")
    public void getCourseWithNonExistentId_TrowsException() throws Exception {
        // Arrange
        Long nonExistentCourseId = 999L;
        String expectedErrorMessage = "Course not found with ID: " + nonExistentCourseId;

        when(getCourseDataUseCase.getCourseData(nonExistentCourseId))
                .thenThrow(new CourseNotFoundException(expectedErrorMessage));

        // Act & Assert
        mockMvc.perform(get("/api/courses/{courseId}", nonExistentCourseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage))
                .andExpect(jsonPath("$.path").value("/api/courses/" + nonExistentCourseId));

        // Verify
        verify(getCourseDataUseCase, times(1)).getCourseData(nonExistentCourseId);
    }

    @Test
    @DisplayName("Update course data - Successful")
    public void updateCourse_Successful() throws Exception {
        // Arrange
        Long courseId = 1L;
        CourseDto courseDto = createCourseDto();

        // Act & Assert
        mockMvc.perform(put("/api/courses/{courseId}", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(courseDto)))
                .andExpect(status().isNoContent());

        // Verify
        verify(updateCourseDataUseCase, times(1)).updateCourse(courseId, courseDto.toCourse());
    }

    @Test
    @DisplayName("Update course with invalid data - Trows Exception")
    public void updateCourseWithInvalidData_TrowsException() throws Exception {
        // Arrange
        Long courseId = 1L;
        CourseDto courseDto = new CourseDto();

        // Act & Assert
        mockMvc.perform(put("/api/courses/{courseId}", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(courseDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value("Validation error"))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.path").value("/api/courses/" + courseId));

        // Verify
        verifyNoInteractions(updateCourseDataUseCase);
    }

    @Test
    @DisplayName("Update non existent course - Trows Exception")
    public void updateNonExistentCourse_TrowsException() throws Exception {
        // Arrange
        Long nonExistentCourseId = 999L;
        CourseDto courseDto = createCourseDto();
        String expectedErrorMessage = "Course not found with ID: " + nonExistentCourseId;

        doThrow(new CourseNotFoundException(expectedErrorMessage))
                .when(updateCourseDataUseCase).updateCourse(nonExistentCourseId, courseDto.toCourse());

        // Act & Assert
        mockMvc.perform(put("/api/courses/{courseId}", nonExistentCourseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(courseDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage))
                .andExpect(jsonPath("$.path").value("/api/courses/" + nonExistentCourseId));

        // Verify
        verify(updateCourseDataUseCase, times(1)).updateCourse(nonExistentCourseId, courseDto.toCourse());
    }

    @Test
    @DisplayName("Delete course - Successful")
    public void deleteCourse_Successful() throws Exception {
        // Arrange
        Long courseId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/courses/{courseId}", courseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify
        verify(deleteCourseUseCase, times(1)).deleteCourse(courseId);
    }

    @Test
    @DisplayName("Delete non existent course - Trows Exception")
    public void deleteNonExistentCourse_TrowsException() throws Exception {
        // Arrange
        Long nonExistentId = 999L;
        String expectedErrorMessage = "Course not found with ID: " + nonExistentId;

        doThrow(new CourseNotFoundException(expectedErrorMessage))
                .when(deleteCourseUseCase).deleteCourse(nonExistentId);

        // Act & Assert
        mockMvc.perform(delete("/api/courses/{courseId}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage))
                .andExpect(jsonPath("$.path").value("/api/courses/" + nonExistentId));

        // Verify
        verify(deleteCourseUseCase, times(1)).deleteCourse(nonExistentId);
    }

    @Test
    @DisplayName("List courses - Successful")
    public void listCourses_Successful() throws Exception {
        // Arrange
        List<Course> expectedCourses = new ArrayList<>();

        expectedCourses.add(new Course(1L, "Engineering", "University", 5d));
        expectedCourses.add(new Course(2L, "Doctorate", "University", 4.5));

        when(listCoursesUseCase.listCourses()).thenReturn(expectedCourses);
        // Act & Assert
        mockMvc.perform(get("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(expectedCourses.size())))
                .andExpect(jsonPath("$[0].id").value(expectedCourses.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(expectedCourses.get(0).getName()))
                .andExpect(jsonPath("$[0].institutionName").value(expectedCourses.get(0).getInstitutionName()))
                .andExpect(jsonPath("$[0].durationInYears").value(expectedCourses.get(0).getDurationInYears()))

                .andExpect(jsonPath("$[1].id").value(expectedCourses.get(1).getId()))
                .andExpect(jsonPath("$[1].name").value(expectedCourses.get(1).getName()))
                .andExpect(jsonPath("$[1].institutionName").value(expectedCourses.get(1).getInstitutionName()))
                .andExpect(jsonPath("$[1].durationInYears").value(expectedCourses.get(1).getDurationInYears()));

        // Verify
        verify(listCoursesUseCase, times(1)).listCourses();
    }

    private CourseDto createCourseDto() {
        return CourseDto.builder()
                .name("Engineering")
                .institutionName("University")
                .durationInYears(5.5)
                .build();
    }

    private Course createCourse() {
        return Course.builder()
                .id(1L)
                .name("Engineering")
                .institutionName("University")
                .durationInYears(5.5)
                .build();
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}