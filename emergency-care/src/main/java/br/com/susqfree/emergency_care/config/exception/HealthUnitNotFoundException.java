package br.com.susqfree.emergency_care.config.exception;

public class HealthUnitNotFoundException extends RuntimeException {
    public HealthUnitNotFoundException(String message) {
        super(message);
    }
}
