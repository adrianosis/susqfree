package br.com.susqfree.emergency_care.config.exception;

public class PatientAlreadyInQueueException extends RuntimeException {
    public PatientAlreadyInQueueException() {
        super("Patient is already in the queue and cannot be registered again.");
    }

    public PatientAlreadyInQueueException(String message) {
        super(message);
    }
}
