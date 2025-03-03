package br.com.susqfree.emergency_care.config.exception;

public class ServiceUnitNotFoundException extends RuntimeException {
    public ServiceUnitNotFoundException(String message) {
        super(message);
    }
}
