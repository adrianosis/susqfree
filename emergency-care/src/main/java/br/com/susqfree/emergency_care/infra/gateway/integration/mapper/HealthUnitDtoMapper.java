package br.com.susqfree.emergency_care.infra.gateway.integration.mapper;

import br.com.susqfree.emergency_care.domain.model.HealthUnit;
import br.com.susqfree.emergency_care.infra.gateway.integration.dto.HealthUnitDto;
import org.springframework.stereotype.Component;

@Component
public class HealthUnitDtoMapper {

    public HealthUnit toDomain(HealthUnitDto healthUnitDto) {
        return new HealthUnit(
                healthUnitDto.getId() ,
                healthUnitDto.getName()
        );
    }

}
