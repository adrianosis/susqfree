package br.com.susqfree.schedule_management.domain.gateway;

import br.com.susqfree.schedule_management.domain.model.Patient;

import java.util.Optional;
import java.util.UUID;

public interface PatientGateway {

    Optional<Patient> findById(UUID patientId);

}
