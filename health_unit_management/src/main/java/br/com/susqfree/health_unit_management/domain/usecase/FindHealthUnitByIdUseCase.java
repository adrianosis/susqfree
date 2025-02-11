package br.com.susqfree.health_unit_management.domain.usecase;

import br.com.susqfree.health_unit_management.domain.gateway.HealthUnitGateway;
import br.com.susqfree.health_unit_management.domain.mapper.HealthUnitMapper;
import br.com.susqfree.health_unit_management.domain.model.HealthUnit;
import br.com.susqfree.health_unit_management.domain.output.HealthUnitOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindHealthUnitByIdUseCase {

    private final HealthUnitGateway healthUnitGateway;

    public HealthUnitOutput execute(Long id) {
        HealthUnit healthUnit = healthUnitGateway.findById(id);
        return HealthUnitMapper.toOutput(healthUnit);
    }

}
