package br.com.susqfree.health_unit_management.infra.gateway.jpa.repository;

import br.com.susqfree.health_unit_management.infra.gateway.jpa.entity.HealthUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthUnitRepository extends JpaRepository<HealthUnitEntity, Long> {
}
