package br.com.susqfree.patient_management.domain.usecase;

import br.com.susqfree.patient_management.domain.gateway.PatientGateway;
import br.com.susqfree.patient_management.domain.input.CreatePatientInput;
import br.com.susqfree.patient_management.domain.mapper.PatientOutputMapper;
import br.com.susqfree.patient_management.domain.model.Address;
import br.com.susqfree.patient_management.domain.model.Patient;
import br.com.susqfree.patient_management.domain.output.PatientOutput;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreatePatientUseCase {

    private final PatientGateway patientGateway;
    private final PatientOutputMapper outputMapper;

    @Transactional(rollbackOn = Exception.class)
    public PatientOutput execute(CreatePatientInput input, UUID patientId) {
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

        Patient patient = Patient.builder()
                .id(patientId)
                .name(input.getName())
                .gender(input.getGender())
                .birthDate(input.getBirthDate())
                .cpf(input.getCpf())
                .susNumber(input.getSusNumber())
                .phoneNumber(input.getPhoneNumber())
                .mail(input.getMail())
                .address(address)
                .build();

        patient = patientGateway.save(patient);

        return outputMapper.toOutput(patient);
    }

}
