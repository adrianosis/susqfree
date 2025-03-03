package br.com.fiap.triage_service.domain.model;

import br.com.fiap.triage_service.infra.gateway.jpa.entity.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class Triage {
    private Integer id;
    private UUID patientId;
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