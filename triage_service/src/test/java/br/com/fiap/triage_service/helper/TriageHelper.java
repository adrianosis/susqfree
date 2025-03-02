package br.com.fiap.triage_service.helper;

import br.com.fiap.triage_service.domain.input.TriageInput;
import br.com.fiap.triage_service.domain.model.Triage;
import br.com.fiap.triage_service.domain.output.TriageOutput;
import br.com.fiap.triage_service.domain.output.TriagePriorityOutput;
import br.com.fiap.triage_service.infra.gateway.jpa.entity.*;

import java.util.Arrays;
import java.util.UUID;

public class TriageHelper {

    public static TriageInput createTriageInput() {
        return TriageInput.builder()
                .patientId(UUID.fromString("328bcfaa-83af-47cc-a732-f9e44e0d7600"))
                .consultationReason("Dor intensa no peito")
                .symptomDuration(SymptomDuration.MENOS_DE_UM_DIA)
                .bodyTemperature(39.5)
                .bloodPressure(BloodPressure.NORMAL)
                .heartRate(HeartRate.TAQUICARDIA)
                .respiratoryRate(RespiratoryRate.NORMAL)
                .consciousnessState(ConsciousnessState.NORMAL)
                .commonSymptoms(Arrays.asList(CommonSymptom.DOR_PEITO, CommonSymptom.FALTA_AR))
                .painIntensity(PainIntensity.INTENSA)
                .bleedingPresent(BleedingPresence.NAO)
                .chronicDiseases(Arrays.asList(ChronicDisease.HIPERTENSAO))
                .continuousMedications(Arrays.asList())
                .pregnant(Pregnancy.NAO)
                .allergies(Arrays.asList())
                .build();
    }

    public static TriageInput createUrgentTriageInput() {
        return TriageInput.builder()
                .patientId(UUID.fromString("328bcfaa-83af-47cc-a732-f9e44e0d7600"))
                .consultationReason("Nausea, vomito e diarreia")
                .symptomDuration(SymptomDuration.QUATRO_A_SETE_DIAS)
                .bodyTemperature(35)
                .bloodPressure(BloodPressure.NORMAL)
                .heartRate(HeartRate.NORMAL)
                .respiratoryRate(RespiratoryRate.NORMAL)
                .consciousnessState(ConsciousnessState.NORMAL)
                .commonSymptoms(Arrays.asList(CommonSymptom.NAUSEA_VOMITO, CommonSymptom.DIARREIA))
                .painIntensity(PainIntensity.INTENSA)
                .bleedingPresent(BleedingPresence.NAO)
                .chronicDiseases(Arrays.asList())
                .continuousMedications(Arrays.asList())
                .pregnant(Pregnancy.NAO)
                .allergies(Arrays.asList())
                .build();
    }

    public static TriageInput createTriageWithoutPriorityInput() {
        return TriageInput.builder()
                .patientId(UUID.fromString("328bcfaa-83af-47cc-a732-f9e44e0d7600"))
                .consultationReason("Dor de cabeça")
                .symptomDuration(SymptomDuration.MENOS_DE_UM_DIA)
                .bodyTemperature(35)
                .bloodPressure(BloodPressure.NORMAL)
                .heartRate(HeartRate.NORMAL)
                .respiratoryRate(RespiratoryRate.NORMAL)
                .consciousnessState(ConsciousnessState.NORMAL)
                .commonSymptoms(Arrays.asList(CommonSymptom.DOR_CABECA))
                .painIntensity(PainIntensity.LEVE)
                .bleedingPresent(BleedingPresence.NAO)
                .chronicDiseases(Arrays.asList())
                .continuousMedications(Arrays.asList())
                .pregnant(Pregnancy.NAO)
                .allergies(Arrays.asList())
                .build();
    }

    public static TriageInput createTriageWithPriorityInput() {
        return TriageInput.builder()
                .patientId(UUID.fromString("328bcfaa-83af-47cc-a732-f9e44e0d7600"))
                .consultationReason("Tosse e Febre")
                .symptomDuration(SymptomDuration.QUATRO_A_SETE_DIAS)
                .bodyTemperature(37.5)
                .bloodPressure(BloodPressure.NORMAL)
                .heartRate(HeartRate.NORMAL)
                .respiratoryRate(RespiratoryRate.NORMAL)
                .consciousnessState(ConsciousnessState.NORMAL)
                .commonSymptoms(Arrays.asList(CommonSymptom.TOSSE, CommonSymptom.FEBRE))
                .painIntensity(PainIntensity.SEM_DOR)
                .bleedingPresent(BleedingPresence.NAO)
                .chronicDiseases(Arrays.asList())
                .continuousMedications(Arrays.asList())
                .pregnant(Pregnancy.NAO)
                .allergies(Arrays.asList())
                .build();
    }

    public static TriageOutput createTriageOutput(Integer id) {
        return TriageOutput.builder()
                .id(id)
                .patientId(UUID.fromString("328bcfaa-83af-47cc-a732-f9e44e0d7600"))
                .consultationReason("Dor intensa no peito")
                .symptomDuration(SymptomDuration.MENOS_DE_UM_DIA)
                .bodyTemperature(39.5)
                .bloodPressure(BloodPressure.NORMAL)
                .heartRate(HeartRate.TAQUICARDIA)
                .respiratoryRate(RespiratoryRate.NORMAL)
                .consciousnessState(ConsciousnessState.NORMAL)
                .commonSymptoms(Arrays.asList(CommonSymptom.DOR_PEITO, CommonSymptom.FALTA_AR))
                .painIntensity(PainIntensity.INTENSA)
                .bleedingPresent(BleedingPresence.NAO)
                .chronicDiseases(Arrays.asList(ChronicDisease.HIPERTENSAO))
                .continuousMedications(Arrays.asList())
                .pregnant(Pregnancy.NAO)
                .allergies(Arrays.asList())
                .build();

    }

    public static TriagePriorityOutput createTriagePriorityOutput() {
        return TriagePriorityOutput.builder()
                .priority("R")
                .diagnosis("Emergência - Atendimento imediato necessário. Condição com risco de vida.")
                .build();
    }

    public static Triage createTriageWithoutPriority(Integer id, PriorityCode priority) {
        return Triage.builder()
                .id(id)
                .patientId(UUID.fromString("328bcfaa-83af-47cc-a732-f9e44e0d7600"))
                .consultationReason("Dor de cabeça")
                .symptomDuration(SymptomDuration.MENOS_DE_UM_DIA)
                .bodyTemperature(35d)
                .bloodPressure(BloodPressure.NORMAL)
                .heartRate(HeartRate.NORMAL)
                .respiratoryRate(RespiratoryRate.NORMAL)
                .consciousnessState(ConsciousnessState.NORMAL)
                .commonSymptoms(Arrays.asList(CommonSymptom.DOR_CABECA))
                .painIntensity(PainIntensity.LEVE)
                .bleedingPresent(BleedingPresence.NAO)
                .chronicDiseases(Arrays.asList())
                .continuousMedications(Arrays.asList())
                .pregnant(Pregnancy.NAO)
                .allergies(Arrays.asList())
                .priority(priority)
                .build();
    }

    public static Triage createUrgentTriage(Integer id, PriorityCode priority) {
        return Triage.builder()
                .id(id)
                .patientId(UUID.fromString("328bcfaa-83af-47cc-a732-f9e44e0d7600"))
                .consultationReason("Nausea, vomito e diarreia")
                .symptomDuration(SymptomDuration.QUATRO_A_SETE_DIAS)
                .bodyTemperature(35d)
                .bloodPressure(BloodPressure.NORMAL)
                .heartRate(HeartRate.NORMAL)
                .respiratoryRate(RespiratoryRate.NORMAL)
                .consciousnessState(ConsciousnessState.NORMAL)
                .commonSymptoms(Arrays.asList(CommonSymptom.NAUSEA_VOMITO, CommonSymptom.DIARREIA))
                .painIntensity(PainIntensity.INTENSA)
                .bleedingPresent(BleedingPresence.NAO)
                .chronicDiseases(Arrays.asList())
                .continuousMedications(Arrays.asList())
                .pregnant(Pregnancy.NAO)
                .allergies(Arrays.asList())
                .priority(priority)
                .build();
    }

    public static Triage createTriageWithPriority(Integer id, PriorityCode priority) {
        return Triage.builder()
                .id(id)
                .patientId(UUID.fromString("328bcfaa-83af-47cc-a732-f9e44e0d7600"))
                .consultationReason("Tosse e Febre")
                .symptomDuration(SymptomDuration.QUATRO_A_SETE_DIAS)
                .bodyTemperature(37.5)
                .bloodPressure(BloodPressure.NORMAL)
                .heartRate(HeartRate.NORMAL)
                .respiratoryRate(RespiratoryRate.NORMAL)
                .consciousnessState(ConsciousnessState.NORMAL)
                .commonSymptoms(Arrays.asList(CommonSymptom.TOSSE, CommonSymptom.FEBRE))
                .painIntensity(PainIntensity.SEM_DOR)
                .bleedingPresent(BleedingPresence.NAO)
                .chronicDiseases(Arrays.asList())
                .continuousMedications(Arrays.asList())
                .pregnant(Pregnancy.NAO)
                .allergies(Arrays.asList())
                .priority(priority)
                .build();
    }

    public static Triage createTriage(Integer id, PriorityCode priority) {
        return Triage.builder()
                .id(id)
                .patientId(UUID.fromString("328bcfaa-83af-47cc-a732-f9e44e0d7600"))
                .consultationReason("Dor intensa no peito")
                .symptomDuration(SymptomDuration.MENOS_DE_UM_DIA)
                .bodyTemperature(39.5)
                .bloodPressure(BloodPressure.NORMAL)
                .heartRate(HeartRate.TAQUICARDIA)
                .respiratoryRate(RespiratoryRate.NORMAL)
                .consciousnessState(ConsciousnessState.NORMAL)
                .commonSymptoms(Arrays.asList(CommonSymptom.DOR_PEITO, CommonSymptom.FALTA_AR))
                .painIntensity(PainIntensity.INTENSA)
                .bleedingPresent(BleedingPresence.NAO)
                .chronicDiseases(Arrays.asList(ChronicDisease.HIPERTENSAO))
                .continuousMedications(Arrays.asList())
                .pregnant(Pregnancy.NAO)
                .allergies(Arrays.asList())
                .priority(priority)
                .build();
    }

    public static TriageEntity createTriageEntity(Integer id, PriorityCode priority) {
        return TriageEntity.builder()
                .id(id)
                .patientId(UUID.fromString("328bcfaa-83af-47cc-a732-f9e44e0d7600"))
                .consultationReason("Dor intensa no peito")
                .symptomDuration(SymptomDuration.MENOS_DE_UM_DIA)
                .bodyTemperature(39.5)
                .bloodPressure(BloodPressure.NORMAL)
                .heartRate(HeartRate.TAQUICARDIA)
                .respiratoryRate(RespiratoryRate.NORMAL)
                .consciousnessState(ConsciousnessState.NORMAL)
                .commonSymptoms(Arrays.asList(CommonSymptom.DOR_PEITO, CommonSymptom.FALTA_AR))
                .painIntensity(PainIntensity.INTENSA)
                .bleedingPresent(BleedingPresence.NAO)
                .chronicDiseases(Arrays.asList(ChronicDisease.HIPERTENSAO))
                .continuousMedications(Arrays.asList())
                .pregnant(Pregnancy.NAO)
                .allergies(Arrays.asList())
                .priority(priority)
                .build();
    }
}
