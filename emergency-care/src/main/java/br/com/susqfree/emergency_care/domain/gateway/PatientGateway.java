package br.com.susqfree.emergency_care.domain.gateway;

import br.com.susqfree.emergency_care.domain.model.Patient;

import java.util.Optional;
import java.util.UUID;

public interface PatientGateway {
    Optional<Patient> findById(UUID patientId);
}
