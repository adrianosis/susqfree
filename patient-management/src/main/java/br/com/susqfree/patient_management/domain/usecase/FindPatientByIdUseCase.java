package br.com.susqfree.patient_management.domain.usecase;

import br.com.susqfree.patient_management.domain.gateway.PatientGateway;
import br.com.susqfree.patient_management.domain.mapper.PatientOutputMapper;
import br.com.susqfree.patient_management.domain.model.Patient;
import br.com.susqfree.patient_management.domain.output.PatientOutput;
import br.com.susqfree.patient_management.infra.exceptions.PatientManagementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindPatientByIdUseCase {

    private final PatientGateway patientGateway;
    private final PatientOutputMapper outputMapper;

    public PatientOutput execute(UUID patientId) {
        Patient patient = patientGateway.findById(patientId)
                .orElseThrow(() -> new PatientManagementException("Patient not found"));

        return outputMapper.toOutput(patient);
    }

}
