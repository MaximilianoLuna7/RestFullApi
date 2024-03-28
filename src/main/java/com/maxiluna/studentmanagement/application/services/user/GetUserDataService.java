package com.maxiluna.studentmanagement.application.services.user;

import com.maxiluna.studentmanagement.domain.exceptions.UserNotFoundException;
import com.maxiluna.studentmanagement.domain.models.Subject;
import com.maxiluna.studentmanagement.domain.models.User;
import com.maxiluna.studentmanagement.domain.usecases.user.GetUserDataUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.UserJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaSubjectRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetUserDataService implements GetUserDataUseCase {
    @Autowired
    private JpaUserRepository userRepository;

    @Override
    public User getUserData(Long userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID: " + userId);
        }

        UserJpa userJpa = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        return userJpa.toUser();
    }
}
