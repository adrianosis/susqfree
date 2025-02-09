package br.com.susqfree.health_unit_management.domain.usecase;

import br.com.susqfree.health_unit_management.domain.gateway.HealthUnitGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteHealthUnitUseCase {

    private final HealthUnitGateway healthUnitGateway;

    public void execute(Long id) {
        healthUnitGateway.delete(id);
    }

}
