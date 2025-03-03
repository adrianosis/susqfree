package br.com.susqfree.patient_management.domain.usecase;

import br.com.susqfree.patient_management.domain.gateway.PatientGateway;
import br.com.susqfree.patient_management.domain.mapper.PatientOutputMapper;
import br.com.susqfree.patient_management.domain.output.PatientOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAllPatientsUseCase {

    private final PatientGateway patientGateway;
    private final PatientOutputMapper outputMapper;

    public Page<PatientOutput> execute(Pageable pageable) {
       var patients = patientGateway.findAll(pageable);

        return patients.map(outputMapper::toOutput);
    }

}
