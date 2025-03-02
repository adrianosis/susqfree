package br.com.susqfree.schedule_management.infra.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class ValidationErrorResponse extends MessageError {

    private Map<String, String> fieldErrors;

}
