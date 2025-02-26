package br.com.susqfree.schedule_management.infra.gateway.integration;

import br.com.susqfree.schedule_management.domain.gateway.DoctorGateway;
import br.com.susqfree.schedule_management.domain.model.Doctor;
import br.com.susqfree.schedule_management.infra.gateway.integration.client.DoctorClient;
import br.com.susqfree.schedule_management.infra.gateway.integration.mapper.DoctorDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DoctorIntegrationGateway implements DoctorGateway {

    private final DoctorClient doctorClient;
    private final DoctorDtoMapper mapper;

    @Override
    public Optional<Doctor> findById(long doctorId) {
        var dto = doctorClient.findDoctorById(doctorId);

        return Optional.ofNullable(mapper.toDomain(dto));
    }

}
