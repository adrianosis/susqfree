package br.com.susqfree.emergency_care.config.exception;

public class AttendanceNotFoundException extends RuntimeException {
    public AttendanceNotFoundException(String message) {
        super(message);
    }
}
