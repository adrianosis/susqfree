package br.com.fiap.triage_service.infra.gateway.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "triage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TriageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "patient_id", nullable = false)
    private Integer patientId;

    @Column(name = "consultation_reason", nullable = false)
    private String consultationReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "symptom_duration")
    private SymptomDuration symptomDuration;

    @Column(name = "body_temperature")
    private Double bodyTemperature;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_pressure")
    private BloodPressure bloodPressure;

    @Enumerated(EnumType.STRING)
    @Column(name = "heart_rate")
    private HeartRate heartRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "respiratory_rate")
    private RespiratoryRate respiratoryRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "consciousness_state")
    private ConsciousnessState consciousnessState;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "triage_common_symptom", joinColumns = @JoinColumn(name = "triage_id"))
    @Column(name = "common_symptom")
    private List<CommonSymptom> commonSymptoms;

    @Enumerated(EnumType.STRING)
    @Column(name = "pain_intensity")
    private PainIntensity painIntensity;

    @Enumerated(EnumType.STRING)
    @Column(name = "bleeding_present")
    private BleedingPresence bleedingPresent;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "triage_chronic_disease", joinColumns = @JoinColumn(name = "triage_id"))
    @Column(name = "chronic_disease")
    private List<ChronicDisease> chronicDiseases;

    @ElementCollection
    @Column(name = "continuous_medications")
    @CollectionTable(name = "triage_continuous_medication", joinColumns = @JoinColumn(name = "triage_id"))
    private List<String> continuousMedications;

    @Enumerated(EnumType.STRING)
    @Column(name = "pregnant")
    private Pregnancy pregnant;

    @ElementCollection
    @Column(name = "allergy_name")
    @CollectionTable(name = "triage_allergy", joinColumns = @JoinColumn(name = "triage_id"))
    private List<String> allergies;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private PriorityCode priority;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
