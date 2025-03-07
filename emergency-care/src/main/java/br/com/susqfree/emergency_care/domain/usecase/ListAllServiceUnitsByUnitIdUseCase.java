package br.com.susqfree.emergency_care.domain.usecase;

import br.com.susqfree.emergency_care.domain.gateway.ServiceUnitGateway;
import br.com.susqfree.emergency_care.domain.model.ServiceUnit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllServiceUnitsByUnitIdUseCase {

    private final ServiceUnitGateway serviceUnitGateway;

    public ListAllServiceUnitsByUnitIdUseCase(ServiceUnitGateway serviceUnitGateway) {
        this.serviceUnitGateway = serviceUnitGateway;
    }

    public List<ServiceUnit> execute(Long unitId) {
        return serviceUnitGateway.findAllByUnitId(unitId);
    }

}
