package br.com.susqfree.schedule_management.infra.gateway.integration;

import br.com.susqfree.schedule_management.domain.gateway.SpecialtyGateway;
import br.com.susqfree.schedule_management.domain.model.Specialty;
import br.com.susqfree.schedule_management.infra.gateway.integration.client.DoctorClient;
import br.com.susqfree.schedule_management.infra.gateway.integration.mapper.SpecialtyDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SpecialtyIntegrationGateway implements SpecialtyGateway {

    private final DoctorClient doctorClient;
    private final SpecialtyDtoMapper mapper;

    @Override
    public Optional<Specialty> findById(long specialtyId) {
        var dto = doctorClient.findSpecialtyById(specialtyId);

        return Optional.ofNullable(mapper.toDomain(dto));
    }

}
