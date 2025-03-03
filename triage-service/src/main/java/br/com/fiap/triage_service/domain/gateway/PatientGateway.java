package br.com.fiap.triage_service.domain.gateway;

import java.util.Optional;
import java.util.UUID;

public interface PatientGateway {

    boolean existsPatient(UUID patientId);

}
