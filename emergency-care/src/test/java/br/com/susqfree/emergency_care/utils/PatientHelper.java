package br.com.susqfree.emergency_care.utils;

import br.com.susqfree.emergency_care.domain.model.Patient;
import br.com.susqfree.emergency_care.infra.gateway.integration.dto.PatientDto;

import java.util.UUID;

public class PatientHelper {

    public static Patient createPatient() {
        return new Patient(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                "João da Silva",
                "123.456.789-00",
                "1234567890"
        );
    }

    public static PatientDto createPatientDto() {
        return new PatientDto(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                "João da Silva",
                "123.456.789-00",
                "1234567890"
        );
    }
}
