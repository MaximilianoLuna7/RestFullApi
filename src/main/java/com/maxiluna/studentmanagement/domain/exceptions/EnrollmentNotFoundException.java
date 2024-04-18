package com.maxiluna.studentmanagement.domain.exceptions;

public class EnrollmentNotFoundException extends RuntimeException {
    public EnrollmentNotFoundException(String message) {
        super(message);
    }

    public EnrollmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
