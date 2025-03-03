package br.com.susqfree.emergency_care.domain.usecase;

import br.com.susqfree.emergency_care.domain.gateway.ServiceUnitGateway;
import br.com.susqfree.emergency_care.domain.model.ServiceUnit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllServiceUnitsUseCase {

    private final ServiceUnitGateway serviceUnitGateway;

    public ListAllServiceUnitsUseCase(ServiceUnitGateway serviceUnitGateway) {
        this.serviceUnitGateway = serviceUnitGateway;
    }

    public List<ServiceUnit> execute() {
        return serviceUnitGateway.findAll();
    }
}
