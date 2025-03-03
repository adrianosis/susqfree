package br.com.susqfree.emergency_care.api.mapper;

import br.com.susqfree.emergency_care.api.dto.ServiceUnitInput;
import br.com.susqfree.emergency_care.api.dto.ServiceUnitOutput;
import br.com.susqfree.emergency_care.domain.model.ServiceUnit;
import org.springframework.stereotype.Component;

@Component
public class ServiceUnitDtoMapper {

    public ServiceUnit toDomain(ServiceUnitInput input) {
        return new ServiceUnit(null, input.getServiceType(), input.getCapacity(), input.getUnitId());
    }

    public ServiceUnitOutput toOutput(ServiceUnit serviceUnit) {
        return new ServiceUnitOutput(
                serviceUnit.getId(),
                serviceUnit.getServiceType(),
                serviceUnit.getCapacity(),
                serviceUnit.getUnitId()
        );
    }
}
