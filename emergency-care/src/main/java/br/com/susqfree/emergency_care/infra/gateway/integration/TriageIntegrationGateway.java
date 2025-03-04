package br.com.susqfree.emergency_care.infra.gateway.integration;


import br.com.susqfree.emergency_care.domain.gateway.TriageGateway;
import br.com.susqfree.emergency_care.domain.model.TriageInput;
import br.com.susqfree.emergency_care.domain.model.TriagePriorityOutput;
import br.com.susqfree.emergency_care.infra.gateway.integration.client.TriageClient;
import br.com.susqfree.emergency_care.infra.gateway.integration.mapper.TriageDtoMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TriageIntegrationGateway implements TriageGateway {

    private final TriageClient triageClient;
    private final TriageDtoMapper mapper;


    public TriagePriorityOutput processTriage(TriageInput triageInput) {
        try {
            var dto = mapper.toDto(triageInput);
            var response = triageClient.processTriage(dto);
            return mapper.toDomain(response);
        } catch (FeignException e) {
            throw new RuntimeException("Erro ao processar a triagem para o paciente com ID " + triageInput.patientId(), e);
        }
    }
}
