package com.maxiluna.studentmanagement.domain.exceptions;

public class AttendanceNotFoundException extends RuntimeException {
    public AttendanceNotFoundException(String message) {
        super(message);
    }

    public AttendanceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
