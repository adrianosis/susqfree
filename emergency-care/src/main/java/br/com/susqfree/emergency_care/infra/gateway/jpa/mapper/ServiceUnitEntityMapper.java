package br.com.susqfree.emergency_care.infra.gateway.jpa.mapper;

import br.com.susqfree.emergency_care.domain.model.ServiceUnit;
import br.com.susqfree.emergency_care.infra.gateway.jpa.entity.ServiceUnitEntity;
import org.springframework.stereotype.Component;

@Component
public class ServiceUnitEntityMapper {

    public ServiceUnit toDomain(ServiceUnitEntity entity) {
        if (entity == null) return null;
        return new ServiceUnit(
                entity.getId(),
                entity.getServiceType(),
                entity.getCapacity(),
                entity.getUnitId()
        );
    }

    public ServiceUnitEntity toEntity(ServiceUnit domain) {
        if (domain == null) return null;
        return new ServiceUnitEntity(
                domain.getId(),
                domain.getServiceType(),
                domain.getCapacity(),
                domain.getUnitId()
        );
    }
}
