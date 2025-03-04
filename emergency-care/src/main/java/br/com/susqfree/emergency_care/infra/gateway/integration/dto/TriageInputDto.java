package br.com.susqfree.emergency_care.infra.gateway.integration.dto;

import java.util.List;
import java.util.UUID;

public record TriageInputDto(
        UUID patientId,
        String consultationReason,
        String symptomDuration,
        double bodyTemperature,
        String bloodPressure,
        String heartRate,
        String respiratoryRate,
        String consciousnessState,
        List<String> commonSymptoms,
        String painIntensity,
        String bleedingPresent,
        List<String> chronicDiseases,
        List<String> continuousMedications,
        String pregnant,
        List<String> allergies
) {}
