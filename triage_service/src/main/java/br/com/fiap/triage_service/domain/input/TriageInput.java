package br.com.fiap.triage_service.domain.input;

import br.com.fiap.triage_service.infra.gateway.jpa.entity.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(
        description = "Entrada de dados para triagem",
        example = """
    {
        "patientId": 12345,
        "consultationReason": "Dor intensa no peito",
        "symptomDuration": "MENOS_DE_UM_DIA",
        "bodyTemperature": 39.5,
        "bloodPressure": "NORMAL",
        "heartRate": "TAQUICARDIA",
        "respiratoryRate": "NORMAL",
        "consciousnessState": "NORMAL",
        "commonSymptoms": ["DOR_PEITO", "FALTA_AR"],
        "painIntensity": "INTENSA",
        "bleedingPresent": "NAO",
        "chronicDiseases": ["HIPERTENSAO"],
        "continuousMedications": [],
        "pregnant": "NAO",
        "allergies": []
    }
    """
)
public class TriageInput {

    @NotNull
    private Integer patientId;
    @NotBlank
    private String consultationReason;
    private SymptomDuration symptomDuration;
    private double bodyTemperature;
    private BloodPressure bloodPressure;
    private HeartRate heartRate;
    private RespiratoryRate respiratoryRate;
    private ConsciousnessState consciousnessState;
    @NotNull
    private List<CommonSymptom> commonSymptoms;
    private PainIntensity painIntensity;
    private BleedingPresence bleedingPresent;
    @NotNull
    private List<ChronicDisease> chronicDiseases;
    @NotNull
    private List<String> continuousMedications;
    private Pregnancy pregnant;
    @NotNull
    private List<String> allergies;

}
