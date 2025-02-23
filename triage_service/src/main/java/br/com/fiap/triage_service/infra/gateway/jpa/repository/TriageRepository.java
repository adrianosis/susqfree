package br.com.fiap.triage_service.infra.gateway.jpa.repository;

import br.com.fiap.triage_service.infra.gateway.jpa.entity.TriageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TriageRepository extends JpaRepository<TriageEntity, Integer> {
    List<TriageEntity> findByPatientId(Integer patientId);
}
