package br.com.susqfree.emergency_care.domain.gateway;

import br.com.susqfree.emergency_care.domain.model.ServiceUnit;

import java.util.List;
import java.util.Optional;

public interface ServiceUnitGateway {

    ServiceUnit save(ServiceUnit serviceUnit);

    Optional<ServiceUnit> findById(Long id);

    List<ServiceUnit> findAllByUnitId(Long unitId);

}
