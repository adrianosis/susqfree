package br.com.susqfree.patient_management.domain.gateway;

import br.com.susqfree.patient_management.domain.model.Patient;

public interface PatientGateway {

    Patient save(Patient patient);

}
