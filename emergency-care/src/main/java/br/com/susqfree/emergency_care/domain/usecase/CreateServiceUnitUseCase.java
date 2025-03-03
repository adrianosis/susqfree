package br.com.susqfree.emergency_care.domain.usecase;

import br.com.susqfree.emergency_care.config.exception.HealthUnitNotFoundException;
import br.com.susqfree.emergency_care.domain.gateway.HealthUnitGateway;
import br.com.susqfree.emergency_care.domain.gateway.ServiceUnitGateway;
import br.com.susqfree.emergency_care.domain.model.HealthUnit;
import br.com.susqfree.emergency_care.domain.model.ServiceUnit;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateServiceUnitUseCase {

    private final ServiceUnitGateway serviceUnitGateway;
    private final HealthUnitGateway healthUnitGateway;

    public CreateServiceUnitUseCase(ServiceUnitGateway serviceUnitGateway, HealthUnitGateway healthUnitGateway) {
        this.serviceUnitGateway = serviceUnitGateway;
        this.healthUnitGateway = healthUnitGateway;
    }

    public ServiceUnit execute(ServiceUnit serviceUnit) {
        Optional<HealthUnit> healthUnit = healthUnitGateway.findById(serviceUnit.getUnitId());
        if (healthUnit.isEmpty()) {
            throw new HealthUnitNotFoundException("Service unit not found");
        }

        return serviceUnitGateway.save(serviceUnit);
    }
}
