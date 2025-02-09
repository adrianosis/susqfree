package br.com.susqfree.health_unit_management.domain.usecase;

import br.com.susqfree.health_unit_management.domain.gateway.HealthUnitGateway;
import br.com.susqfree.health_unit_management.domain.input.HealthUnitInput;
import br.com.susqfree.health_unit_management.domain.mapper.HealthUnitMapper;
import br.com.susqfree.health_unit_management.domain.model.HealthUnit;
import br.com.susqfree.health_unit_management.domain.output.HealthUnitOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateHealthUnitUseCase {

    private final HealthUnitGateway healthUnitGateway;

    public HealthUnitOutput execute(HealthUnitInput input) {

        HealthUnit healthUnit = HealthUnitMapper.toDomain(input);

        healthUnit = healthUnitGateway.save(healthUnit);

        return HealthUnitMapper.toOutput(healthUnit);
    }

}
