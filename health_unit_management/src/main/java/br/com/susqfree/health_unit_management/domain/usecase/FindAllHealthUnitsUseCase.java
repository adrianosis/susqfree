package br.com.susqfree.health_unit_management.domain.usecase;

import br.com.susqfree.health_unit_management.domain.gateway.HealthUnitGateway;
import br.com.susqfree.health_unit_management.domain.mapper.HealthUnitMapper;
import br.com.susqfree.health_unit_management.domain.output.HealthUnitOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindAllHealthUnitsUseCase {

    private final HealthUnitGateway healthUnitGateway;

    public List<HealthUnitOutput> execute() {
        return healthUnitGateway.findAll()
                .stream()
                .map(HealthUnitMapper::toOutput)
                .collect(Collectors.toList());
    }
}
