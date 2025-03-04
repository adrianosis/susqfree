package br.com.susqfree.emergency_care.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(
        description = "Entrada de dados para triagem",
        example = """
    {
        "patientId": "328bcfaa-83af-47cc-a732-f9e44e0d7600",
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
public record TriageInput(
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
