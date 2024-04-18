package com.maxiluna.studentmanagement.application.services.user;

import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.exceptions.UserNotFoundException;
import com.maxiluna.studentmanagement.domain.usecases.user.DeleteUserUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.UserJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteUserService implements DeleteUserUseCase {

    @Autowired
    private JpaUserRepository userRepository;

    @Override
    @Transactional
    public void execute(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        try {
            UserJpa userJpa = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found."));

            userRepository.delete(userJpa);
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error deleting user account: " + ex.getMessage());
        }

    }
}
