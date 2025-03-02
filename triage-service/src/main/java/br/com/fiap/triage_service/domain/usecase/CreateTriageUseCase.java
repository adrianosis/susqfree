package br.com.fiap.triage_service.domain.usecase;

import br.com.fiap.triage_service.domain.gateway.PatientGateway;
import br.com.fiap.triage_service.domain.gateway.TriageGateway;
import br.com.fiap.triage_service.domain.input.TriageInput;
import br.com.fiap.triage_service.domain.mapper.TriageMapper;
import br.com.fiap.triage_service.domain.model.Triage;
import br.com.fiap.triage_service.domain.output.TriagePriorityOutput;
import br.com.fiap.triage_service.infra.gateway.jpa.entity.CommonSymptom;
import br.com.fiap.triage_service.infra.gateway.jpa.entity.PriorityCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class CreateTriageUseCase {

    private final TriageGateway triageGateway;
    private final PatientGateway patientGateway;

    public TriagePriorityOutput execute(TriageInput input) {
        if(!patientGateway.existsPatient(input.getPatientId()))
            throw new IllegalArgumentException("Patient not found");

        Triage triage = TriageMapper.toDomain(input);
        triage.setPriority(calculatePriority(triage));
        triage = triageGateway.create(triage);
        return TriageMapper.toPriorityOutput(triage);
    }

    private PriorityCode calculatePriority(Triage triage) {
        int totalSeverity = 0;

        totalSeverity += getCommonSymptomsSeverity(triage.getCommonSymptoms());
        totalSeverity += (triage.getRespiratoryRate() != null) ? triage.getRespiratoryRate().getSeverityLevel() : 0;
        totalSeverity += (triage.getBloodPressure() != null) ? triage.getBloodPressure().getSeverityLevel() : 0;
        totalSeverity += (triage.getPainIntensity() != null) ? triage.getPainIntensity().getSeverityLevel() : 0;
        totalSeverity += (triage.getConsciousnessState() != null) ? triage.getConsciousnessState().getSeverityLevel() : 0;
        totalSeverity += (triage.getHeartRate() != null) ? triage.getHeartRate().getSeverityLevel() : 0;
        totalSeverity += (triage.getPregnant() != null) ? triage.getPregnant().getSeverityLevel() : 0;
        totalSeverity += (triage.getBleedingPresent() != null) ? triage.getBleedingPresent().getSeverityLevel() : 0;
        totalSeverity += getBodyTemperatureSeverity(triage.getBodyTemperature());
        totalSeverity += (isEmpty(triage.getChronicDiseases()) ) ? 1 : 0;

        return getPriorityCode(totalSeverity);
    }

    private int getCommonSymptomsSeverity(List<CommonSymptom> symptoms) {
        if (symptoms == null || symptoms.isEmpty()) return 0;
        return symptoms.stream().mapToInt(CommonSymptom::getSeverityLevel).sum();
    }

    private int getBodyTemperatureSeverity(Double temperature) {
        if (temperature == null) return 0;
        if (temperature >= 38.0) return 3;
        if (temperature >= 37.5) return 2;
        return 0;
    }

    private PriorityCode getPriorityCode(int totalSeverity) {
        if (totalSeverity >= 15) return PriorityCode.R;
        if (totalSeverity >= 10) return PriorityCode.Y;
        if (totalSeverity >= 5) return PriorityCode.G;
        return PriorityCode.B;
    }

}
