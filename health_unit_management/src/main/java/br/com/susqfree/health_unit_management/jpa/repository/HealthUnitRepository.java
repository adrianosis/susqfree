package br.com.susqfree.health_unit_management.jpa.repository;

import br.com.susqfree.health_unit_management.jpa.entity.HealthUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthUnitRepository extends JpaRepository<HealthUnitEntity, Long> {
}
