package br.com.susqfree.schedule_management.domain.gateway;

import br.com.susqfree.schedule_management.domain.model.Specialty;

import java.util.Optional;

public interface SpecialtyGateway {

    Optional<Specialty> findById(long specialtyId);

}
