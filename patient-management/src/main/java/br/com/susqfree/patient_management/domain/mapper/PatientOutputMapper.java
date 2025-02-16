package br.com.susqfree.patient_management.domain.mapper;

import br.com.susqfree.patient_management.domain.model.Patient;
import br.com.susqfree.patient_management.domain.output.PatientOutput;
import org.springframework.stereotype.Component;

@Component
public class PatientOutputMapper {

    public PatientOutput toOutput(Patient patient) {
        return PatientOutput.builder()
                .id(patient.getId())
                .name(patient.getName())
                .gender(patient.getGender())
                .birthDate(patient.getBirthDate())
                .cpf(patient.getCpf())
                .susNumber(patient.getSusNumber())
                .phoneNumber(patient.getPhoneNumber())
                .mail(patient.getMail())
                .street(patient.getAddress().getStreet())
                .number(patient.getAddress().getNumber())
                .complement(patient.getAddress().getComplement())
                .district(patient.getAddress().getDistrict())
                .city(patient.getAddress().getCity())
                .state(patient.getAddress().getState())
                .postalCode(patient.getAddress().getPostalCode())
                .longitude(patient.getAddress().getLongitude())
                .latitude(patient.getAddress().getLatitude())
                .build();
    }

}
