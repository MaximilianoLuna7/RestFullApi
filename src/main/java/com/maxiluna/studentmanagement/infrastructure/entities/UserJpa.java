package com.maxiluna.studentmanagement.infrastructure.entities;

import com.maxiluna.studentmanagement.domain.models.Subject;
import com.maxiluna.studentmanagement.domain.models.User;
import com.maxiluna.studentmanagement.domain.models.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class UserJpa{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String role;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SubjectJpa> subjects = new ArrayList<>();

    public static UserJpa fromUser(User user) {
        UserJpa userJpa = UserJpa.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .role(user.getRole().name())
                .build();

        List<SubjectJpa> subjectsJpa = user.getSubjects().stream()
                .map(SubjectJpa::fromSubject)
                .collect(Collectors.toList());
        userJpa.setSubjects(subjectsJpa);

        return userJpa;
    }

    public User toUser() {
        User user = User.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .birthDate(this.birthDate)
                .role(UserRole.valueOf(String.valueOf(this.role)))
                .build();

        Set<Subject> subjects = this.subjects.stream()
                .map(SubjectJpa::toSubject)
                .collect(Collectors.toSet());
        user.setSubjects(subjects);

        return user;
    }
}
