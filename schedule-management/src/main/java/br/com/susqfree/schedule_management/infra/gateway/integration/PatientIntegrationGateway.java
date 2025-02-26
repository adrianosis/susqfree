package br.com.susqfree.schedule_management.infra.gateway.integration;

import br.com.susqfree.schedule_management.domain.gateway.PatientGateway;
import br.com.susqfree.schedule_management.domain.model.Patient;
import br.com.susqfree.schedule_management.infra.gateway.integration.client.PatientClient;
import br.com.susqfree.schedule_management.infra.gateway.integration.mapper.PatientDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PatientIntegrationGateway implements PatientGateway {

    private final PatientClient patientClient;
    private final PatientDtoMapper mapper;

    @Override
    public Optional<Patient> findById(UUID patientId) {
        var dto = patientClient.findById(patientId);

        return Optional.ofNullable(mapper.toDomain(dto));
    }

}
