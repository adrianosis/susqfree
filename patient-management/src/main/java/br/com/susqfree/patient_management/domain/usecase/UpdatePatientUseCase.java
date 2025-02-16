package br.com.susqfree.patient_management.domain.usecase;

import br.com.susqfree.patient_management.domain.gateway.PatientGateway;
import br.com.susqfree.patient_management.domain.input.UpdatePatientInput;
import br.com.susqfree.patient_management.domain.mapper.PatientOutputMapper;
import br.com.susqfree.patient_management.domain.model.Address;
import br.com.susqfree.patient_management.domain.model.Patient;
import br.com.susqfree.patient_management.domain.output.PatientOutput;
import br.com.susqfree.patient_management.infra.exceptions.PatientManagementException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdatePatientUseCase {

    private final PatientGateway patientGateway;
    private final PatientOutputMapper outputMapper;

    @Transactional(rollbackOn = Exception.class)
    public PatientOutput execute(UUID patientId, UpdatePatientInput input) {
        Patient patient = patientGateway.findById(patientId)
                .orElseThrow(() -> new PatientManagementException("Patient not found"));
                
        patient.updateName(input.getName());
        patient.updateGender(input.getGender());
        patient.updatePhoneNumber(input.getPhoneNumber());
        patient.updateMail(input.getMail());

        Address address = Address.builder()
                .street(input.getStreet())
                .number(input.getNumber())
                .complement(input.getComplement())
                .district(input.getDistrict())
                .city(input.getCity())
                .state(input.getState())
                .postalCode(input.getPostalCode())
                .latitude(input.getLatitude())
                .longitude(input.getLongitude())
                .build();
        patient.updateAddress(address);

        patient = patientGateway.save(patient);

        return outputMapper.toOutput(patient);
    }

}
