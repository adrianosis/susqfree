package br.com.susqfree.patient_management.infra.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class ValidationErrorResponse extends MessageError {

    private Map<String, String> fieldErrors;

}
