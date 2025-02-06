package br.com.susqfree.patient_management.domain.usecase;

import br.com.susqfree.patient_management.domain.gateway.PatientGateway;
import br.com.susqfree.patient_management.domain.input.PatientInput;
import br.com.susqfree.patient_management.domain.mapper.PatientMapper;
import br.com.susqfree.patient_management.domain.model.Patient;
import br.com.susqfree.patient_management.domain.output.PatientOutput;
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
