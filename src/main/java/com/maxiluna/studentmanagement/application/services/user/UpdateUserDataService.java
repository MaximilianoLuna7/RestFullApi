package com.maxiluna.studentmanagement.application.services.user;

import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.exceptions.UserNotFoundException;
import com.maxiluna.studentmanagement.domain.models.User;
import com.maxiluna.studentmanagement.domain.usecases.user.UpdateUserDataUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.UserJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateUserDataService implements UpdateUserDataUseCase {

    @Autowired
    private JpaUserRepository userRepository;

    @Override
    @Transactional
    public void updateUserData(Long userId, User updatedUser) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID: " + userId);
        }

        try {
            UserJpa userJpa = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

            userJpa.setFirstName(updatedUser.getFirstName());
            userJpa.setLastName(updatedUser.getLastName());
            userJpa.setBirthDate(updatedUser.getBirthDate());

            userRepository.save(userJpa);
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error updating user data: " + ex.getMessage());
        }
    }
}
