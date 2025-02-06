package br.com.susqfree.patient.infra.gateway.jpa.repository;

import br.com.susqfree.patient.infra.gateway.jpa.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
}
