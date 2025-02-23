package br.com.fiap.triage_service.infra.gateway.jpa.mapper;


import br.com.fiap.triage_service.domain.model.Triage;
import br.com.fiap.triage_service.infra.gateway.jpa.entity.*;

public class TriageEntityMapper {
    public static TriageEntity toEntity(Triage triage) {
        return TriageEntity.builder()
                .id(triage.getId())
                .patientId(triage.getPatientId())
                .consultationReason(triage.getConsultationReason())
                .symptomDuration(triage.getSymptomDuration())
                .bodyTemperature(triage.getBodyTemperature())
                .bloodPressure(triage.getBloodPressure())
                .heartRate(triage.getHeartRate())
                .respiratoryRate(triage.getRespiratoryRate())
                .consciousnessState(triage.getConsciousnessState())
                .commonSymptoms(triage.getCommonSymptoms())
                .painIntensity(triage.getPainIntensity())
                .bleedingPresent(triage.getBleedingPresent())
                .chronicDiseases(triage.getChronicDiseases())
                .continuousMedications(triage.getContinuousMedications())
                .pregnant(triage.getPregnant())
                .allergies(triage.getAllergies())
                .priority(triage.getPriority())
                .createdAt(triage.getCreatedAt())
                .build();
    }

    public static Triage toDomain(TriageEntity triageEntity) {
        return Triage.builder()
                .id(triageEntity.getId())
                .patientId(triageEntity.getPatientId())
                .consultationReason(triageEntity.getConsultationReason())
                .symptomDuration(triageEntity.getSymptomDuration())
                .bodyTemperature(triageEntity.getBodyTemperature())
                .bloodPressure(triageEntity.getBloodPressure())
                .heartRate(triageEntity.getHeartRate())
                .respiratoryRate(triageEntity.getRespiratoryRate())
                .consciousnessState(triageEntity.getConsciousnessState())
                .commonSymptoms(triageEntity.getCommonSymptoms())
                .painIntensity(triageEntity.getPainIntensity())
                .bloodPressure(triageEntity.getBloodPressure())
                .heartRate(triageEntity.getHeartRate())
                .respiratoryRate(triageEntity.getRespiratoryRate())
                .consciousnessState(triageEntity.getConsciousnessState())
                .pregnant(triageEntity.getPregnant())
                .allergies(triageEntity.getAllergies())
                .priority(triageEntity.getPriority())
                .createdAt(triageEntity.getCreatedAt())
                .build();
    }
}
