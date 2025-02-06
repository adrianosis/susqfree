package br.com.susqfree.patient_management.infra.gateway.jpa.repository;

import br.com.susqfree.patient_management.infra.gateway.jpa.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
}
