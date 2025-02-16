package br.com.susqfree.patient_management.infra.gateway.jpa;

import br.com.susqfree.patient_management.domain.gateway.PatientGateway;
import br.com.susqfree.patient_management.domain.model.Patient;
import br.com.susqfree.patient_management.infra.exceptions.PatientManagementException;
import br.com.susqfree.patient_management.infra.gateway.jpa.entity.PatientEntity;
import br.com.susqfree.patient_management.infra.gateway.jpa.mapper.PatientEntityMapper;
import br.com.susqfree.patient_management.infra.gateway.jpa.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PatientJpaGateway implements PatientGateway {

    private final PatientRepository repository;
    private final PatientEntityMapper mapper;

    @Override
    public Patient save(Patient patient) {
        try {
            PatientEntity patientEntity = mapper.toEntity(patient);
            patientEntity = repository.save(patientEntity);

            return mapper.toDomain(patientEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PatientManagementException("Failed to access data repository");
        }
    }

    @Override
    public Optional<Patient> findById(UUID patientId) {
        try {
            Optional<PatientEntity> patientEntity = repository.findById(patientId);

            return patientEntity.map(mapper::toDomain);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PatientManagementException("Failed to access data repository");
        }
    }

    @Override
    public Optional<Patient> findByCpf(String cpf) {
        try {
            Optional<PatientEntity> patientEntity = repository.findByCpf(cpf);

            return patientEntity.map(mapper::toDomain);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PatientManagementException("Failed to access data repository");
        }
    }

}
