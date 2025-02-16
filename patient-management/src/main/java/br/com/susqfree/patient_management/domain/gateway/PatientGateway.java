package br.com.susqfree.patient_management.domain.gateway;

import br.com.susqfree.patient_management.domain.model.Patient;

import java.util.Optional;
import java.util.UUID;

public interface PatientGateway {

    Patient save(Patient patient);

    Optional<Patient> findById(UUID patientId);

    Optional<Patient> findByCpf(String cpf);

}
