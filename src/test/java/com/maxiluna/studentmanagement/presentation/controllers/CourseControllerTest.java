package com.maxiluna.studentmanagement.presentation.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxiluna.studentmanagement.config.jwt.JwtAuthenticationFilter;
import com.maxiluna.studentmanagement.domain.exceptions.CourseNotFoundException;
import com.maxiluna.studentmanagement.domain.models.Course;
import com.maxiluna.studentmanagement.domain.usecases.course.*;
import com.maxiluna.studentmanagement.presentation.dtos.course.CourseResponseDto;
import com.maxiluna.studentmanagement.presentation.dtos.course.CreateCourseDto;
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
    private WebApplicationContext webApplicationContext;

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
        CreateCourseDto createCourseDto = createCourseDto();
        Course course = createCourseDto.toCourse();

        // Act & Assert
        mockMvc.perform(post("/api/courses/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(createCourseDto)))
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
        course.setSubjects(new ArrayList<>());

        CourseResponseDto responseCourseDto = CourseResponseDto.fromCourse(course);

        when(getCourseDataUseCase.getCourseData(courseId)).thenReturn(course);

        // Act & Assert
        mockMvc.perform(get("/api/courses/{courseId}", courseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseCourseDto.getId()))
                .andExpect(jsonPath("$.name").value(responseCourseDto.getName()))
                .andExpect(jsonPath("$.institutionName").value(responseCourseDto.getInstitutionName()))
                .andExpect(jsonPath("$.durationInYears").value(responseCourseDto.getDurationInYears()));

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
    @DisplayName("Get course data with non existent ID - Throws Exception")
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
        CreateCourseDto createCourseDto = createCourseDto();

        // Act & Assert
        mockMvc.perform(put("/api/courses/{courseId}", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createCourseDto)))
                .andExpect(status().isNoContent());

        // Verify
        verify(updateCourseDataUseCase, times(1)).updateCourse(courseId, createCourseDto.toCourse());
    }

    @Test
    @DisplayName("Update course with invalid data - Trows Exception")
    public void updateCourseWithInvalidData_TrowsException() throws Exception {
        // Arrange
        Long courseId = 1L;
        CreateCourseDto createCourseDto = new CreateCourseDto();

        // Act & Assert
        mockMvc.perform(put("/api/courses/{courseId}", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createCourseDto)))
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
        CreateCourseDto createCourseDto = createCourseDto();
        String expectedErrorMessage = "Course not found with ID: " + nonExistentCourseId;

        doThrow(new CourseNotFoundException(expectedErrorMessage))
                .when(updateCourseDataUseCase).updateCourse(nonExistentCourseId, createCourseDto.toCourse());

        // Act & Assert
        mockMvc.perform(put("/api/courses/{courseId}", nonExistentCourseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createCourseDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage))
                .andExpect(jsonPath("$.path").value("/api/courses/" + nonExistentCourseId));

        // Verify
        verify(updateCourseDataUseCase, times(1)).updateCourse(nonExistentCourseId, createCourseDto.toCourse());
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

        Course course1 = Course.builder()
                .id(1L)
                .name("Engineering")
                .institutionName("University")
                .durationInYears(3.5)
                .subjects(new ArrayList<>())
                .build();
        Course course2 = Course.builder()
                .id(2L)
                .name("Doctorate")
                .institutionName("University")
                .durationInYears(4d)
                .subjects(new ArrayList<>())
                .build();

        expectedCourses.add(course1);
        expectedCourses.add(course2);

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

    private CreateCourseDto createCourseDto() {
        return CreateCourseDto.builder()
                .name("Engineering")
                .institutionName("University")
                .durationInYears(5.5)
                .build();
    }

    private CourseResponseDto createResponseCourseDto() {
        return CourseResponseDto.builder()
                .id(1L)
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