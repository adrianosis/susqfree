package br.com.susqfree.schedule_management.infra.gateway.integration;

import br.com.susqfree.schedule_management.domain.gateway.HealthUnitGateway;
import br.com.susqfree.schedule_management.domain.model.HealthUnit;
import br.com.susqfree.schedule_management.infra.gateway.integration.client.HealthUnitClient;
import br.com.susqfree.schedule_management.infra.gateway.integration.mapper.HealthUnitDtoMapper;
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
        var dto = healthUnitClient.findById(healthUnitId);

        return Optional.ofNullable(mapper.toDomain(dto));
    }

}
