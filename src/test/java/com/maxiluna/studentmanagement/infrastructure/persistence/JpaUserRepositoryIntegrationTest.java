package com.maxiluna.studentmanagement.infrastructure.persistence;

import com.maxiluna.studentmanagement.infrastructure.entities.UserJpa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class JpaUserRepositoryIntegrationTest {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Test
    @DisplayName("Save user - Successful")
    public void saveUser_Successful() {
        // Arrange
        UserJpa user = createUserJpa();

        // Act
        UserJpa savedUser = jpaUserRepository.save(user);

        // Assert
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("Find user by ID - Successful")
    public void findUserById_Successful() {
        // Arrange
        UserJpa user = createUserJpa();
        UserJpa savedUser = jpaUserRepository.save(user);

        // Act
        Optional<UserJpa> foundUserOptional = jpaUserRepository.findById(savedUser.getId());

        // Assert
        assertThat(foundUserOptional).isPresent();
        assertThat(foundUserOptional.get().getId()).isEqualTo(savedUser.getId());
    }

    @Test
    @DisplayName("Find user by ID - Not found")
    public void findUserById_NotFound() {
        // Arrange
        Long nonExistentId = 999L;

        // Act
        Optional<UserJpa> foundUserOptional = jpaUserRepository.findById(nonExistentId);

        // Assert
        assertThat(foundUserOptional).isEmpty();
    }

    @Test
    @DisplayName("Find all users - Successful")
    public void findAllUsers_Successful() {
        // Arrange
        UserJpa user1 = createUserJpa();
        UserJpa user2 = UserJpa.builder()
                .email("jane.smith@example.com")
                .password("password")
                .firstName("Jane")
                .lastName("Smith")
                .birthDate(LocalDate.of(1995,10,13))
                .role("TEACHER")
                .build();

        jpaUserRepository.save(user1);
        jpaUserRepository.save(user2);

        // Act
        List<UserJpa> foundUsers = jpaUserRepository.findAll();

        // Assert
        assertThat(foundUsers)
                .isNotNull()
                .hasSize(2);
        assertThat(foundUsers).contains(user1, user2);
    }

    @Test
    @DisplayName("Delete user - Successful")
    public void deleteUser_Successful() {
        // Arrange
        UserJpa user = createUserJpa();
        jpaUserRepository.save(user);

        // Act
        jpaUserRepository.delete(user);

        // Assert
        assertThat(jpaUserRepository.existsByEmail(user.getEmail())).isFalse();
    }

    @Test
    @DisplayName("Exists by email - Returns true")
    public void existsByEmail_ReturnsTrue() {
        // Arrange
        UserJpa user = createUserJpa();
        jpaUserRepository.save(user);

        // Act
        boolean exists = jpaUserRepository.existsByEmail(user.getEmail());

        // Assert
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Exists by email - Returns false")
    public void existsByEmail_ReturnsFalse() {
        // Arrange
        String nonExistentEmail = "nonexistent@example.com";

        // Act
        boolean exists = jpaUserRepository.existsByEmail(nonExistentEmail);

        // Assert
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Find by email - Successful")
    public void findByEmail_Successful() {
        // Arrange
        UserJpa user = createUserJpa();
        jpaUserRepository.save(user);

        // Act
        Optional<UserJpa> optionalFoundUser = jpaUserRepository.findByEmail(user.getEmail());

        // Assert
        assertThat(optionalFoundUser).isPresent();
        assertThat(optionalFoundUser.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("Find by email - Non existing user ")
    public void findByEmail_NonExistingUser() {
        // Arrange
        String nonExistentEmail = "nonexistent@example.com";

        // Act
        Optional<UserJpa> optionalFoundUser = jpaUserRepository.findByEmail(nonExistentEmail);

        // Assert
        assertThat(optionalFoundUser).isEmpty();
    }

    private UserJpa createUserJpa() {
        return UserJpa.builder()
                .email("john.doe@example.com")
                .password("password123")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(2000,2,5))
                .role("ADMIN")
                .build();
    }
}