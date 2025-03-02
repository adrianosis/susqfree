package br.com.fiap.triage_service.domain.mapper;


import br.com.fiap.triage_service.domain.input.TriageInput;
import br.com.fiap.triage_service.domain.model.Triage;
import br.com.fiap.triage_service.domain.output.TriageOutput;
import br.com.fiap.triage_service.domain.output.TriagePriorityOutput;

public class TriageMapper {

    public static TriagePriorityOutput toPriorityOutput(Triage triage) {
        return  TriagePriorityOutput.builder()
                .priority(triage.getPriority().name())
                .diagnosis(triage.getPriority().getDiagnosis())
                .build();
    }

    public static TriageOutput toOutput(Triage triage) {
        return TriageOutput.builder()
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

    public static Triage toDomain(TriageInput input) {
        return Triage.builder()
                .patientId(input.getPatientId())
                .consultationReason(input.getConsultationReason())
                .symptomDuration(input.getSymptomDuration())
                .bodyTemperature(input.getBodyTemperature())
                .bloodPressure(input.getBloodPressure())
                .heartRate(input.getHeartRate())
                .respiratoryRate(input.getRespiratoryRate())
                .consciousnessState(input.getConsciousnessState())
                .commonSymptoms(input.getCommonSymptoms())
                .painIntensity(input.getPainIntensity())
                .bleedingPresent(input.getBleedingPresent())
                .chronicDiseases(input.getChronicDiseases())
                .continuousMedications(input.getContinuousMedications())
                .pregnant(input.getPregnant())
                .allergies(input.getAllergies())
                .build();
    }

}
