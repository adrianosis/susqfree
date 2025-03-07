package br.com.susqfree.health_unit_management.infra.gateway.jpa.repository;

import br.com.susqfree.health_unit_management.infra.gateway.jpa.entity.HealthUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HealthUnitRepository extends JpaRepository<HealthUnitEntity, Long> {
    List<HealthUnitEntity> findAllByCityAndState(String city, String state);
}
