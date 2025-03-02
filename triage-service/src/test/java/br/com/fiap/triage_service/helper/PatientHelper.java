package br.com.fiap.triage_service.helper;


import br.com.fiap.triage_service.infra.gateway.integration.dto.PatientDto;

import java.util.UUID;

public class PatientHelper {

    public static PatientDto createPatientDto() {
        return PatientDto.builder()
                .id(UUID.fromString("9b410f3c-356e-4f76-9d0f-e7fc22a47ba8"))
                .name("John Doe")
                .cpf("123456789")
                .susNumber("12345678912340")
                .build();
    }

}
