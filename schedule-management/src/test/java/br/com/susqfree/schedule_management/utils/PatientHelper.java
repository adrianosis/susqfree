package br.com.susqfree.schedule_management.utils;

import br.com.susqfree.schedule_management.domain.model.Patient;
import br.com.susqfree.schedule_management.domain.output.PatientOutput;
import br.com.susqfree.schedule_management.infra.gateway.db.mongo.document.PatientDocument;
import br.com.susqfree.schedule_management.infra.gateway.integration.dto.PatientDto;

import java.util.UUID;

public class PatientHelper {

    public static Patient createPatient() {
        return Patient.builder()
                .id(UUID.fromString("67f74a7b-08a6-4680-81af-4ceb036bb387"))
                .name("John Doe")
                .cpf("123456789")
                .susNumber("12345678912340")
                .build();
    }

    public static PatientDocument createPatientDocument() {
        return PatientDocument.builder()
                .id(UUID.fromString("67f74a7b-08a6-4680-81af-4ceb036bb387"))
                .name("John Doe")
                .cpf("123456789")
                .susNumber("12345678912340")
                .build();
    }

    public static PatientOutput createPatientOutput() {
        return PatientOutput.builder()
                .id(UUID.fromString("67f74a7b-08a6-4680-81af-4ceb036bb387"))
                .name("John Doe")
                .cpf("123456789")
                .susNumber("12345678912340")
                .build();
    }

    public static PatientDto createPatientDto() {
        return PatientDto.builder()
                .id(UUID.fromString("67f74a7b-08a6-4680-81af-4ceb036bb387"))
                .name("John Doe")
                .cpf("123456789")
                .susNumber("12345678912340")
                .build();
    }

}
