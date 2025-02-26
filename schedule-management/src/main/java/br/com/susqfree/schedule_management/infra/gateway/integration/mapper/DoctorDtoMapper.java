package br.com.susqfree.schedule_management.infra.gateway.integration.mapper;

import br.com.susqfree.schedule_management.domain.model.Doctor;
import br.com.susqfree.schedule_management.infra.gateway.integration.dto.DoctorDto;
import org.springframework.stereotype.Component;

@Component
public class DoctorDtoMapper {

    public Doctor toDomain(DoctorDto doctorDto) {
        return Doctor.builder()
                .id(doctorDto.getId())
                .name(doctorDto.getName())
                .crm(doctorDto.getCrm())
                .build();
    }

}
