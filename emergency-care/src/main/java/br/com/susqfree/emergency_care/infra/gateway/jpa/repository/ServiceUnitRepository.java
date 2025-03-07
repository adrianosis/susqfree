package br.com.susqfree.emergency_care.infra.gateway.jpa.repository;

import br.com.susqfree.emergency_care.infra.gateway.jpa.entity.ServiceUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceUnitRepository extends JpaRepository<ServiceUnitEntity, Long> {
    List<ServiceUnitEntity> findAllByUnitId(Long unitId);
}
