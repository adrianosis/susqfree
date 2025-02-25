package br.com.fiap.triage_service.domain.usecase;

import br.com.fiap.triage_service.domain.gateway.TriageGateway;
import br.com.fiap.triage_service.domain.input.TriageInput;
import br.com.fiap.triage_service.domain.mapper.TriageMapper;
import br.com.fiap.triage_service.domain.model.Triage;
import br.com.fiap.triage_service.domain.output.TriagePriorityOutput;
import br.com.fiap.triage_service.infra.gateway.jpa.entity.CommonSymptom;
import br.com.fiap.triage_service.infra.gateway.jpa.entity.PriorityCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTriageUseCase {

    private final TriageGateway triageGateway;

    public TriagePriorityOutput execute(TriageInput input) {

        Triage triage = TriageMapper.toDomain(input);

        processTriage(triage);

        triage = triageGateway.create(triage);

        return TriageMapper.toPriorityOutput(triage);
    }

    public void processTriage(Triage triage) {
        int totalSeverity = 0;

        // Common Symptoms
        for (CommonSymptom symptom : triage.getCommonSymptoms()) {
            totalSeverity += symptom.getSeverityLevel();
        }

        // Respiratory Rate
        if (triage.getRespiratoryRate() != null) {
            totalSeverity += triage.getRespiratoryRate().getSeverityLevel();
        }

        // Blood Pressure
        if (triage.getBloodPressure() != null) {
            totalSeverity += triage.getBloodPressure().getSeverityLevel();
        }

        // Pain Intensity
        if (triage.getPainIntensity() != null) {
            totalSeverity += triage.getPainIntensity().getSeverityLevel();
        }

        // Body Temperature
        if (triage.getBodyTemperature() != null) {
            if (triage.getBodyTemperature() >= 38.0) {
                totalSeverity += 3; // High fever
            } else if (triage.getBodyTemperature() >= 37.5) {
                totalSeverity += 2; // Moderate fever
            }
        }

        // Chronic Diseases
        if (triage.getChronicDiseases() != null && !triage.getChronicDiseases().isEmpty()) {
            totalSeverity += 2; // Chronic conditions
        }

        PriorityCode priorityCode;
        if (totalSeverity >= 15) {
            priorityCode = PriorityCode.R;
        } else if (totalSeverity >= 10) {
            priorityCode = PriorityCode.Y;
        } else if (totalSeverity >= 5) {
            priorityCode = PriorityCode.G;
        } else {
            priorityCode = PriorityCode.B;
        }

        triage.setPriority(priorityCode);
    }
}
