package br.com.susqfree.patient.infra.gateway.jpa;

import br.com.susqfree.patient.domain.gateway.PatientGateway;
import br.com.susqfree.patient.domain.model.Patient;
import br.com.susqfree.patient.infra.gateway.jpa.entity.PatientEntity;
import br.com.susqfree.patient.infra.gateway.jpa.mapper.PatientEntityMapper;
import br.com.susqfree.patient.infra.gateway.jpa.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientJpaGateway implements PatientGateway {

    private final PatientRepository repository;

    @Override
    public Patient save(Patient patient) {
        PatientEntity patientEntity = PatientEntityMapper.toEntity(patient);

        patientEntity = repository.save(patientEntity);

        return PatientEntityMapper.toDomain(patientEntity);
    }

}
