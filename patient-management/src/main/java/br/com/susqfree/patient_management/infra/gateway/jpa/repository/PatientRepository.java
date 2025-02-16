package br.com.susqfree.patient_management.infra.gateway.jpa.repository;

import br.com.susqfree.patient_management.infra.gateway.jpa.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<PatientEntity, UUID> {

    Optional<PatientEntity> findByCpf(String cpf);

}
