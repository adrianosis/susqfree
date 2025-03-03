package br.com.susqfree.emergency_care.domain.gateway;


import br.com.susqfree.emergency_care.domain.model.HealthUnit;

import java.util.Optional;

public interface HealthUnitGateway {

    Optional<HealthUnit> findById(long healthUnitId);

}
