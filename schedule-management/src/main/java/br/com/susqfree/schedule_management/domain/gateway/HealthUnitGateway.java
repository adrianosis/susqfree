package br.com.susqfree.schedule_management.domain.gateway;

import br.com.susqfree.schedule_management.domain.model.HealthUnit;

import java.util.Optional;

public interface HealthUnitGateway {

    Optional<HealthUnit> findById(long healthUnitId);

}
