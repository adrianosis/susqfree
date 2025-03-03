package br.com.susqfree.emergency_care.domain.usecase;

import br.com.susqfree.emergency_care.domain.gateway.ServiceUnitGateway;
import br.com.susqfree.emergency_care.domain.model.ServiceUnit;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindServiceUnitByIdUseCase {

    private final ServiceUnitGateway serviceUnitGateway;

    public FindServiceUnitByIdUseCase(ServiceUnitGateway serviceUnitGateway) {
        this.serviceUnitGateway = serviceUnitGateway;
    }

    public Optional<ServiceUnit> execute(Long id) {
        return serviceUnitGateway.findById(id);
    }
}
