package br.com.susqfree.schedule_management.infra.gateway.integration.mapper;

import br.com.susqfree.schedule_management.domain.model.HealthUnit;
import br.com.susqfree.schedule_management.infra.gateway.integration.dto.HealthUnitDto;
import org.springframework.stereotype.Component;

@Component
public class HealthUnitDtoMapper {

    public HealthUnit toDomain(HealthUnitDto healthUnitDto) {
        return HealthUnit.builder()
                .id(healthUnitDto.getId())
                .name(healthUnitDto.getName())
                .build();
    }

}
