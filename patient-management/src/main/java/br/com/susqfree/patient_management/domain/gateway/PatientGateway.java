package br.com.susqfree.patient_management.domain.gateway;

import br.com.susqfree.patient_management.domain.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface PatientGateway {

    Patient save(Patient patient);

    Optional<Patient> findById(UUID patientId);

    Optional<Patient> findByCpf(String cpf);

    Page<Patient> findAll(Pageable pageable);

}
