package br.com.susqfree.patient_management.infra.gateway.jpa.mapper;

import br.com.susqfree.patient_management.domain.model.Patient;
import br.com.susqfree.patient_management.infra.gateway.jpa.entity.PatientEntity;

public class PatientEntityMapper {

    public static Patient toDomain(PatientEntity patientEntity) {
        return Patient.builder()
                .id(patientEntity.getId())
                .name(patientEntity.getName())
                .cpf(patientEntity.getCpf())
                .build();
    }

    public static PatientEntity toEntity(Patient patient) {
        return PatientEntity.builder()
                .id(patient.getId())
                .name(patient.getName())
                .cpf(patient.getCpf())
                .build();
    }

}
