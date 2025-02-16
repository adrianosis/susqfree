package br.com.susqfree.patient_management.infra.exceptions;

public class PatientManagementException extends RuntimeException {

    public PatientManagementException(String message){
        super(message);
    }

}
