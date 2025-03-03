package br.com.susqfree.emergency_care.infra.gateway.integration;

import br.com.susqfree.emergency_care.config.exception.HealthUnitNotFoundException;
import br.com.susqfree.emergency_care.domain.gateway.HealthUnitGateway;
import br.com.susqfree.emergency_care.domain.model.HealthUnit;
import br.com.susqfree.emergency_care.infra.gateway.integration.client.HealthUnitClient;
import br.com.susqfree.emergency_care.infra.gateway.integration.mapper.HealthUnitDtoMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HealthUnitIntegrationGateway implements HealthUnitGateway {

    private final HealthUnitClient healthUnitClient;
    private final HealthUnitDtoMapper mapper;

    @Override
    public Optional<HealthUnit> findById(long healthUnitId) {
        try {
            var dto = healthUnitClient.findById(healthUnitId);
            return Optional.ofNullable(mapper.toDomain(dto));
        } catch (FeignException e) {
            throw new HealthUnitNotFoundException("Unidade de saúde com ID " + healthUnitId + " não encontrada.");
        }
    }

}
