package br.com.susqfree.patient_management.infra.gateway.jpa.mapper;

import br.com.susqfree.patient_management.domain.model.Address;
import br.com.susqfree.patient_management.domain.model.Patient;
import br.com.susqfree.patient_management.infra.gateway.jpa.entity.AddressEntity;
import br.com.susqfree.patient_management.infra.gateway.jpa.entity.PatientEntity;
import org.springframework.stereotype.Component;

@Component
public class PatientEntityMapper {

    public Patient toDomain(PatientEntity patientEntity) {
        Address address = Address.builder()
                .street(patientEntity.getAddress().getStreet())
                .number(patientEntity.getAddress().getNumber())
                .complement(patientEntity.getAddress().getComplement())
                .district(patientEntity.getAddress().getDistrict())
                .city(patientEntity.getAddress().getCity())
                .state(patientEntity.getAddress().getState())
                .postalCode(patientEntity.getAddress().getPostalCode())
                .longitude(patientEntity.getAddress().getLongitude())
                .latitude(patientEntity.getAddress().getLatitude())
                .build();

        return Patient.builder()
                .id(patientEntity.getId())
                .name(patientEntity.getName())
                .gender(patientEntity.getGender())
                .cpf(patientEntity.getCpf())
                .susNumber(patientEntity.getSusNumber())
                .birthDate(patientEntity.getBirthDate())
                .phoneNumber(patientEntity.getPhoneNumber())
                .mail(patientEntity.getMail())
                .address(address)
                .build();
    }

    public PatientEntity toEntity(Patient patient) {
        AddressEntity address = AddressEntity.builder()
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


        return PatientEntity.builder()
                .id(patient.getId())
                .name(patient.getName())
                .gender(patient.getGender())
                .cpf(patient.getCpf())
                .susNumber(patient.getSusNumber())
                .susNumber(patient.getSusNumber())
                .birthDate(patient.getBirthDate())
                .phoneNumber(patient.getPhoneNumber())
                .mail(patient.getMail())
                .address(address)
                .build();
    }

}
