package com.maxiluna.studentmanagement.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    private Long id;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email must not be blank")
    @Size(min = 2, max = 255, message = "Email must be between 2 and 255 characters")
    private String email;

    @NotBlank(message = "Password must not be blank")
    private String password;

    @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
    private String firstName;

    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    private String lastName;

    @Past(message = "Birth date must be in the past")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @NotNull(message = "Role must not be null")
    private UserRole role;

    private Set<Subject> subjects = new HashSet<>();

    public void addSubject(Subject subjectToAdd) {
        subjects.add(subjectToAdd);
    }

    public void removeSubject(Subject subjectToRemove) {
        subjects.remove(subjectToRemove);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}