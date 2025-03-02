package br.com.susqfree.patient_management.infra.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ValidationErrorResponse extends MessageError {

    private Map<String, String> fieldErrors;

}
