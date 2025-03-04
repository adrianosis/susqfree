package br.com.susqfree.emergency_care.infra.gateway.integration.mapper;

import br.com.susqfree.emergency_care.domain.model.TriageInput;
import br.com.susqfree.emergency_care.domain.model.TriagePriorityOutput;
import br.com.susqfree.emergency_care.infra.gateway.integration.dto.TriageInputDto;
import br.com.susqfree.emergency_care.infra.gateway.integration.dto.TriagePriorityOutputDto;
import org.springframework.stereotype.Component;

@Component
public class TriageDtoMapper {

    public TriageInputDto toDto(TriageInput domain) {
        return new TriageInputDto(
                domain.patientId(),
                domain.consultationReason(),
                domain.symptomDuration(),
                domain.bodyTemperature(),
                domain.bloodPressure(),
                domain.heartRate(),
                domain.respiratoryRate(),
                domain.consciousnessState(),
                domain.commonSymptoms(),
                domain.painIntensity(),
                domain.bleedingPresent(),
                domain.chronicDiseases(),
                domain.continuousMedications(),
                domain.pregnant(),
                domain.allergies()
        );
    }

    public TriagePriorityOutput toDomain(TriagePriorityOutputDto dto) {
        return new TriagePriorityOutput(dto.priority(), dto.diagnosis());
    }
}
