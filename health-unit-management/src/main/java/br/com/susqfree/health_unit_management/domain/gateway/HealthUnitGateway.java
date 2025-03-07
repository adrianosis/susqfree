package br.com.susqfree.health_unit_management.domain.gateway;

import br.com.susqfree.health_unit_management.domain.model.HealthUnit;

import java.util.List;

public interface HealthUnitGateway {

    HealthUnit save(HealthUnit healthUnit);
    HealthUnit update(Long id, HealthUnit healthUnit);
    HealthUnit findById(Long id);
    List<HealthUnit> findAllByCityAndState(String city, String state);

}
