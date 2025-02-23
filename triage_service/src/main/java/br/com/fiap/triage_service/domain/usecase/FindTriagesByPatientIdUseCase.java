package br.com.fiap.triage_service.domain.usecase;

import br.com.fiap.triage_service.domain.gateway.TriageGateway;
import br.com.fiap.triage_service.domain.input.TriageInput;
import br.com.fiap.triage_service.domain.mapper.TriageMapper;
import br.com.fiap.triage_service.domain.model.Triage;
import br.com.fiap.triage_service.domain.output.TriageOutput;
import br.com.fiap.triage_service.domain.output.TriagePriorityOutput;
import br.com.fiap.triage_service.infra.gateway.jpa.entity.CommonSymptom;
import br.com.fiap.triage_service.infra.gateway.jpa.entity.PriorityCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindTriagesByPatientIdUseCase {

    private final TriageGateway triageGateway;

    public List<TriageOutput> execute(Integer patientId) {
        List<Triage> triages = triageGateway.findByPatientId(patientId);
        return triages.stream()
                .map(TriageMapper::toOutput)
                .collect(Collectors.toList());
    }
}
