package com.maxiluna.studentmanagement.domain.exceptions;

public class ClassRecordNotFoundException extends RuntimeException {
    public ClassRecordNotFoundException(String message) {
        super(message);
    }

    public ClassRecordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
