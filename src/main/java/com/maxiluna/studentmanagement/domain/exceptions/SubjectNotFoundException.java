package com.maxiluna.studentmanagement.domain.exceptions;

public class SubjectNotFoundException extends RuntimeException{
    public SubjectNotFoundException(String message) {
        super(message);
    }

    public SubjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
