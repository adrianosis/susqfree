package br.com.susqfree.emergency_care.infra.gateway.integration;

import br.com.susqfree.emergency_care.config.exception.PatientNotFoundException;
import br.com.susqfree.emergency_care.domain.gateway.PatientGateway;
import br.com.susqfree.emergency_care.domain.model.Patient;
import br.com.susqfree.emergency_care.infra.gateway.integration.client.PatientClient;
import br.com.susqfree.emergency_care.infra.gateway.integration.mapper.PatientDtoMapper;
import feign.FeignException;
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
        try {
            var dto = patientClient.findById(patientId);
            return Optional.ofNullable(mapper.toDomain(dto));
        } catch (FeignException e) {
            throw new PatientNotFoundException("Paciente com ID " + patientId + " não encontrado.");
        }
    }
}
