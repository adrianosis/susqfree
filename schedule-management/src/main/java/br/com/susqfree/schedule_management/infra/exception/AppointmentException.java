package br.com.susqfree.schedule_management.infra.exception;

public class AppointmentException extends RuntimeException {

    public AppointmentException(String message){
        super(message);
    }

}
