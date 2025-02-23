package br.com.fiap.triage_service.domain.output;

import br.com.fiap.triage_service.infra.gateway.jpa.entity.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TriageOutput {
    private Integer id;
    private Integer patientId;
    private String consultationReason;
    private SymptomDuration symptomDuration;
    private Double bodyTemperature;
    private BloodPressure bloodPressure;
    private HeartRate heartRate;
    private RespiratoryRate respiratoryRate;
    private ConsciousnessState consciousnessState;
    private List<CommonSymptom> commonSymptoms;
    private PainIntensity painIntensity;
    private BleedingPresence bleedingPresent;
    private List<ChronicDisease> chronicDiseases;
    private List<String> continuousMedications;
    private Pregnancy pregnant;
    private List<String> allergies;
    private PriorityCode priority;
    private LocalDateTime createdAt;
}