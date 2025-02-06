package br.com.susqfree.patient.domain.usecase;

import br.com.susqfree.patient.domain.gateway.PatientGateway;
import br.com.susqfree.patient.domain.input.PatientInput;
import br.com.susqfree.patient.domain.mapper.PatientMapper;
import br.com.susqfree.patient.domain.model.Patient;
import br.com.susqfree.patient.domain.output.PatientOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePatientUseCase {

    private final PatientGateway patientGateway;

    public PatientOutput execute(PatientInput input) {

        Patient patient = Patient.builder()
                .build();

        patient = patientGateway.save(patient);

        return PatientMapper.toOutput(patient);
    }

}
