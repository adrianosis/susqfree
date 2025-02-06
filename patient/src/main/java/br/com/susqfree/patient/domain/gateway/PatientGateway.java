package br.com.susqfree.patient.domain.gateway;

import br.com.susqfree.patient.domain.model.Patient;

public interface PatientGateway {

    Patient save(Patient patient);

}
