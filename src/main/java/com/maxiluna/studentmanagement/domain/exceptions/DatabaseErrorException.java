package com.maxiluna.studentmanagement.domain.exceptions;

public class DatabaseErrorException extends RuntimeException {
    public DatabaseErrorException(String message) {
        super(message);
    }

    public DatabaseErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
