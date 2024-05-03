package com.maxiluna.studentmanagement.domain.exceptions;

public class GradeNotFoundException extends RuntimeException{
    public GradeNotFoundException(String message) {
        super(message);
    }

    public GradeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
