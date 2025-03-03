package br.com.susqfree.patient_management.infra.gateway.jpa;

import br.com.susqfree.patient_management.domain.gateway.PatientGateway;
import br.com.susqfree.patient_management.domain.model.Patient;
import br.com.susqfree.patient_management.infra.gateway.jpa.entity.PatientEntity;
import br.com.susqfree.patient_management.infra.gateway.jpa.mapper.PatientEntityMapper;
import br.com.susqfree.patient_management.infra.gateway.jpa.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PatientJpaGateway implements PatientGateway {

    private final PatientRepository repository;
    private final PatientEntityMapper mapper;

    @Override
    public Patient save(Patient patient) {
        PatientEntity patientEntity = mapper.toEntity(patient);
        patientEntity = repository.save(patientEntity);

        return mapper.toDomain(patientEntity);
    }

    @Override
    public Optional<Patient> findById(UUID patientId) {
        Optional<PatientEntity> patientEntity = repository.findById(patientId);

        return patientEntity.map(mapper::toDomain);
    }

    @Override
    public Optional<Patient> findByCpf(String cpf) {
        Optional<PatientEntity> patientEntity = repository.findByCpf(cpf);

        return patientEntity.map(mapper::toDomain);
    }

    @Override
    public Page<Patient> findAll(Pageable pageable) {
        Page<PatientEntity> patientEntity = repository.findAll(pageable);

        return patientEntity.map(mapper::toDomain);
    }

}
