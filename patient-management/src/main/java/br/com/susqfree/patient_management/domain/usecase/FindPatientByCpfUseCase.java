package br.com.susqfree.patient_management.domain.usecase;

import br.com.susqfree.patient_management.domain.gateway.PatientGateway;
import br.com.susqfree.patient_management.domain.mapper.PatientOutputMapper;
import br.com.susqfree.patient_management.domain.model.Patient;
import br.com.susqfree.patient_management.domain.output.PatientOutput;
import br.com.susqfree.patient_management.infra.exceptions.PatientManagementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindPatientByCpfUseCase {

    private final PatientGateway patientGateway;
    private final PatientOutputMapper outputMapper;

    public PatientOutput execute(String cpf) {
        Patient patient = patientGateway.findByCpf(cpf)
                .orElseThrow(() -> new PatientManagementException("Patient not found"));

        return outputMapper.toOutput(patient);
    }

}
