package br.com.susqfree.emergency_care.infra.gateway.integration.mapper;

import br.com.susqfree.emergency_care.domain.model.Patient;
import br.com.susqfree.emergency_care.infra.gateway.integration.dto.PatientDto;
import org.springframework.stereotype.Component;

@Component
public class PatientDtoMapper {
    public Patient toDomain(PatientDto dto) {
        return new Patient(
                dto.getId(),
                dto.getName(),
                dto.getCpf(),
                dto.getSusNumber()
        );
    }
}
