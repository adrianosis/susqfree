package br.com.susqfree.schedule_management.infra.gateway.integration.mapper;

import br.com.susqfree.schedule_management.domain.model.Patient;
import br.com.susqfree.schedule_management.infra.gateway.integration.dto.PatientDto;
import org.springframework.stereotype.Component;

@Component
public class PatientDtoMapper {

    public Patient toDomain(PatientDto patientDto) {
        return Patient.builder()
                .id(patientDto.getId())
                .name(patientDto.getName())
                .cpf(patientDto.getCpf())
                .susNumber(patientDto.getSusNumber())
                .build();
    }

}
