package br.com.susqfree.doctor_management.infra.gateway.jpa.repository;

import br.com.susqfree.doctor_management.infra.gateway.jpa.entity.SpecialtyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialtyRepository extends JpaRepository<SpecialtyEntity, Long> {
}
