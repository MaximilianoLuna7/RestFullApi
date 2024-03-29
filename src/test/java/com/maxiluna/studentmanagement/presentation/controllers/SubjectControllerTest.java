package com.maxiluna.studentmanagement.presentation.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxiluna.studentmanagement.application.services.subject.GetSubjectsByCourseService;
import com.maxiluna.studentmanagement.config.jwt.JwtAuthenticationFilter;
import com.maxiluna.studentmanagement.domain.exceptions.SubjectNotFoundException;
import com.maxiluna.studentmanagement.domain.models.Course;
import com.maxiluna.studentmanagement.domain.models.Subject;
import com.maxiluna.studentmanagement.domain.models.User;
import com.maxiluna.studentmanagement.domain.models.UserRole;
import com.maxiluna.studentmanagement.domain.usecases.subject.*;
import com.maxiluna.studentmanagement.presentation.dtos.course.CourseResponseDto;
import com.maxiluna.studentmanagement.presentation.dtos.subject.SubjectRequestDto;
import com.maxiluna.studentmanagement.presentation.dtos.subject.SubjectResponseDto;
import com.maxiluna.studentmanagement.presentation.dtos.user.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubjectController.class)
class SubjectControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CreateSubjectUseCase createSubjectUseCase;

    @MockBean
    private GetSubjectUseCase getSubjectUseCase;

    @MockBean
    private UpdateSubjectUseCase updateSubjectUseCase;

    @MockBean
    private DeleteSubjectUseCase deleteSubjectUseCase;

    @MockBean
    private ListSubjectsUseCase listSubjectsUseCase;

    @MockBean
    private GetSubjectsByTeacherUseCase getSubjectsByTeacherUseCase;

    @MockBean
    private GetSubjectsByCourseService getSubjectsByCourseService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Create subject - Successful")
    public void createSubject_Successful() throws Exception {
        // Arrange
        SubjectRequestDto subjectToCreateDto = createRequestDto();
        Subject subjectToCreate = subjectToCreateDto.toSubject();
        Long courseId = subjectToCreateDto.getCourseId();
        Long teacherId = subjectToCreateDto.getTeacherId();

        // Act & Assert
        mockMvc.perform(post("/api/subjects/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(subjectToCreateDto)))
                .andExpect(status().isNoContent());

        // Verify
        verify(createSubjectUseCase, times(1)).createSubject(subjectToCreate, courseId, teacherId);
    }

    @Test
    @DisplayName("Get subject - Successful")
    public void getSubject_Successful() throws Exception {
        // Arrange
        SubjectResponseDto subjectDto = createResponseDto();
        Long subjectId = subjectDto.getId();

        User teacher = User.builder()
                .id(1L)
                .email("john.doe@example.com")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1995,5,5))
                .role(UserRole.TEACHER)
                .subjects(new ArrayList<>())
                .build();

        Course course = Course.builder()
                .id(1L)
                .name("Engineering")
                .institutionName("University")
                .durationInYears(5.5)
                .subjects(new ArrayList<>())
                .build();

        Subject subject = Subject.builder()
                .id(subjectDto.getId())
                .name(subjectDto.getName())
                .academicYear(subjectDto.getAcademicYear())
                .course(course)
                .teacher(teacher)
                .build();

        when(getSubjectUseCase.getSubject(subjectId)).thenReturn(subject);

        // Act & Assert
        mockMvc.perform(get("/api/subjects/{subjectId}", subjectId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(subjectDto.getId()))
                .andExpect(jsonPath("$.name").value(subjectDto.getName()))
                .andExpect(jsonPath("$.academicYear").value(subjectDto.getAcademicYear()));

        // Verify
        verify(getSubjectUseCase, times(1)).getSubject(subjectId);
    }

    @Test
    @DisplayName("Get subject with invalid ID - Throws Exception")
    public void getSubjectWithInvalidId_TrowsException() throws Exception {
        // Arrange
        Long invalidSubjectId = -1L;
        String expectedErrorMessage = "Invalid subject ID: " + invalidSubjectId;

        when(getSubjectUseCase.getSubject(invalidSubjectId))
                .thenThrow(new IllegalArgumentException(expectedErrorMessage));

        // Act & Assert
        mockMvc.perform(get("/api/subjects/{subjectId}", invalidSubjectId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage))
                .andExpect(jsonPath("$.path").value("/api/subjects/" + invalidSubjectId));

        // Verify
        verify(getSubjectUseCase, times(1)).getSubject(invalidSubjectId);
    }

    @Test
    @DisplayName("Get subject with non existent ID - Throws Exception")
    public void getSubjectWithNonExistentId_TrowsException() throws Exception {
        // Arrange
        Long nonExistentSubjectId = 999L;
        String expectedErrorMessage = "Subject not found with ID: " + nonExistentSubjectId;

        when(getSubjectUseCase.getSubject(nonExistentSubjectId))
                .thenThrow(new SubjectNotFoundException(expectedErrorMessage));

        // Act & Assert
        mockMvc.perform(get("/api/subjects/{subjectId}", nonExistentSubjectId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage))
                .andExpect(jsonPath("$.path").value("/api/subjects/" + nonExistentSubjectId));

        // Verify
        verify(getSubjectUseCase, times(1)).getSubject(nonExistentSubjectId);
    }

    @Test
    @DisplayName("Update subject - Successful")
    public void updateSubject_Successful() throws Exception {
        // Arrange
        Long subjectId = 1L;
        SubjectRequestDto updatedSubjectDto = createRequestDto();

        // Act & Assert
        mockMvc.perform(put("/api/subjects/{subjectId}", subjectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedSubjectDto)))
                .andExpect(status().isNoContent());

        // Verify
        verify(updateSubjectUseCase, times(1)).updateSubject(subjectId, updatedSubjectDto.toSubject());
    }

    @Test
    @DisplayName("Update subject with invalid data - Trows Exception")
    public void updateSubjectWithInvalidData_TrowsException() throws Exception {
        // Arrange
        Long subjectId = 1L;
        SubjectRequestDto invalidSubjectDto = new SubjectRequestDto();

        // Act & Assert
        mockMvc.perform(put("/api/subjects/{subjectId}", subjectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidSubjectDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value("Validation error"))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.path").value("/api/subjects/" + subjectId));

        // Verify
        verifyNoInteractions(updateSubjectUseCase);
    }

    @Test
    @DisplayName("Update non existent subject - Trows Exception")
    public void updateNonExistentSubject_TrowsException() throws Exception {
        // Arrange
        Long nonExistentSubjectId = 999L;
        SubjectRequestDto updatedSubjectDto = createRequestDto();

        String expectedErrorMessage = "Subject not found with ID: " + nonExistentSubjectId;

        doThrow(new SubjectNotFoundException(expectedErrorMessage))
                .when(updateSubjectUseCase).updateSubject(nonExistentSubjectId, updatedSubjectDto.toSubject());

        // Act & Assert
        mockMvc.perform(put("/api/subjects/{subjectId}", nonExistentSubjectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedSubjectDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage))
                .andExpect(jsonPath("$.path").value("/api/subjects/" + nonExistentSubjectId));

        // Verify
        verify(updateSubjectUseCase, times(1)).updateSubject(nonExistentSubjectId, updatedSubjectDto.toSubject());
    }

    @Test
    @DisplayName("Delete subject - Successful")
    public void deleteSubject_Successful() throws Exception {
        // Arrange
        Long subjectId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/subjects/{subjectId}", subjectId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify
        verify(deleteSubjectUseCase, times(1)).deleteSubject(subjectId);
    }

    @Test
    @DisplayName("Delete non existent subject - Trows Exception")
    public void deleteNonExistentSubject_TrowsException() throws Exception {
        // Arrange
        Long nonExistentId = 999L;
        String expectedErrorMessage = "Subject not found with ID: " + nonExistentId;

        doThrow(new SubjectNotFoundException(expectedErrorMessage))
                .when(deleteSubjectUseCase).deleteSubject(nonExistentId);

        // Act & Assert
        mockMvc.perform(delete("/api/subjects/{subjectId}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage))
                .andExpect(jsonPath("$.path").value("/api/subjects/" + nonExistentId));

        // Verify
        verify(deleteSubjectUseCase, times(1)).deleteSubject(nonExistentId);
    }

    @Test
    @DisplayName("List subjects - Successful")
    public void listSubjects_Successful() throws Exception {
        // Arrange
        List<Subject> expectedSubjects = new ArrayList<>();

        User teacher = User.builder()
                .id(1L)
                .email("john.doe@example.com")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1995,5,5))
                .role(UserRole.TEACHER)
                .subjects(new ArrayList<>())
                .build();

        Course course = Course.builder()
                .id(1L)
                .name("Engineering")
                .institutionName("University")
                .durationInYears(5.5)
                .subjects(new ArrayList<>())
                .build();

        Subject subject1 = Subject.builder()
                .id(1L)
                .name("Algebra")
                .academicYear(2022)
                .course(course)
                .teacher(teacher)
                .build();

        Subject subject2 = Subject.builder()
                .id(2L)
                .name("Math")
                .academicYear(2023)
                .course(course)
                .teacher(teacher)
                .build();

        expectedSubjects.add(subject1);
        expectedSubjects.add(subject2);

        when(listSubjectsUseCase.listSubjects()).thenReturn(expectedSubjects);

        // Act & Assert
        mockMvc.perform(get("/api/subjects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(expectedSubjects.size())))
                .andExpect(jsonPath("$[0].id").value(expectedSubjects.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(expectedSubjects.get(0).getName()))
                .andExpect(jsonPath("$[0].academicYear").value(expectedSubjects.get(0).getAcademicYear()))

                .andExpect(jsonPath("$[1].id").value(expectedSubjects.get(1).getId()))
                .andExpect(jsonPath("$[1].name").value(expectedSubjects.get(1).getName()))
                .andExpect(jsonPath("$[1].academicYear").value(expectedSubjects.get(1).getAcademicYear()));

        // Verify
        verify(listSubjectsUseCase, times(1)).listSubjects();
    }

    private SubjectRequestDto createRequestDto() {
        return SubjectRequestDto.builder()
                .name("Algebra")
                .academicYear(2022)
                .courseId(1L)
                .teacherId(1L)
                .build();
    }

    private SubjectResponseDto createResponseDto() {
        return SubjectResponseDto.builder()
                .id(1L)
                .name("Algebra")
                .academicYear(2022)
                .teacher(createTeacherDto())
                .course(createCourseDto())
                .build();
    }

    private CourseResponseDto createCourseDto() {
        return CourseResponseDto.builder()
                .id(1L)
                .name("Engineering")
                .institutionName("University")
                .durationInYears(5.5)
                .build();
    }

    private UserResponseDto createTeacherDto() {
        return UserResponseDto.builder()
                .id(1L)
                .email("john.doe@example.com")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1995,5,5))
                .role(UserRole.TEACHER)
                .build();
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}