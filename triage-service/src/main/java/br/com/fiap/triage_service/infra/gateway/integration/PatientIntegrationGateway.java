package br.com.fiap.triage_service.infra.gateway.integration;

import br.com.fiap.triage_service.domain.gateway.PatientGateway;
import br.com.fiap.triage_service.infra.gateway.integration.client.PatientClient;
import br.com.fiap.triage_service.infra.gateway.integration.dto.PatientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
@RequiredArgsConstructor
public class PatientIntegrationGateway implements PatientGateway {

    @Autowired
    private final PatientClient patientClient;

    public boolean existsPatient(UUID patientId) {
        PatientDto dto = patientClient.findById(patientId);
        return !isEmpty(dto);
    }

}
