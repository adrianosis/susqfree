package br.com.susqfree.schedule_management.infra.gateway.integration.mapper;

import br.com.susqfree.schedule_management.domain.model.Specialty;
import br.com.susqfree.schedule_management.infra.gateway.integration.dto.SpecialtyDto;
import org.springframework.stereotype.Component;

@Component
public class SpecialtyDtoMapper {

    public Specialty toDomain(SpecialtyDto specialtyDto) {
        return Specialty.builder()
                .id(specialtyDto.getId())
                .name(specialtyDto.getName())
                .build();
    }

}
