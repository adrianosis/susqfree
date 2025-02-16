package br.com.susqfree.patient_management.utils;

import br.com.susqfree.patient_management.domain.input.CreatePatientInput;
import br.com.susqfree.patient_management.domain.model.Address;
import br.com.susqfree.patient_management.domain.model.Patient;
import br.com.susqfree.patient_management.domain.output.PatientOutput;
import br.com.susqfree.patient_management.infra.gateway.jpa.entity.AddressEntity;
import br.com.susqfree.patient_management.infra.gateway.jpa.entity.PatientEntity;

import java.time.LocalDate;
import java.util.UUID;

public class PatientHelper {

    public static CreatePatientInput createPatientInput() {
        return CreatePatientInput.builder()
                .name("Julia")
                .gender("F")
                .birthDate(LocalDate.of(2005, 6, 24))
                .cpf("11133355500")
                .susNumber("123412341234")
                .phoneNumber("11999995555")
                .mail("julia@test.com")
                .street("Av. Paulista")
                .number("1400")
                .district("Bela Vista")
                .city("São Paulo")
                .state("SP")
                .postalCode("12345678")
                .latitude(-23.562642)
                .longitude(-46.654887)
                .build();
    }

    public static PatientOutput createPatientOutput(UUID patientId) {
        return PatientOutput.builder()
                .id(patientId)
                .name("Julia")
                .gender("F")
                .birthDate(LocalDate.of(2005, 6, 24))
                .cpf("11133355500")
                .susNumber("123412341234")
                .phoneNumber("11999995555")
                .mail("julia@test.com")
                .street("Av. Paulista")
                .number("1400")
                .district("Bela Vista")
                .city("São Paulo")
                .state("SP")
                .postalCode("12345678")
                .latitude(-23.562642)
                .longitude(-46.654887)
                .build();
    }

    public static Patient createPatient(UUID patientId) {
        Address address = Address.builder()
                .street("Av. Paulista")
                .number("1400")
                .district("Bela Vista")
                .city("São Paulo")
                .state("SP")
                .postalCode("12345678")
                .latitude(-23.562642)
                .longitude(-46.654887)
                .build();

        return Patient.builder()
                .id(patientId)
                .name("Julia")
                .gender("F")
                .birthDate(LocalDate.of(2005, 6, 24))
                .cpf("11133355500")
                .susNumber("123412341234")
                .phoneNumber("11999995555")
                .mail("julia@test.com")
                .address(address)
                .build();
    }

    public static PatientEntity createPatientEntity(UUID patientId) {
        AddressEntity address = AddressEntity.builder()
                .street("Av. Paulista")
                .number("1400")
                .district("Bela Vista")
                .city("São Paulo")
                .state("SP")
                .postalCode("12345678")
                .build();
        return PatientEntity.builder()
                .id(patientId)
                .name("Julia")
                .gender("F")
                .birthDate(LocalDate.of(2005, 6, 24))
                .cpf("11133355500")
                .susNumber("123412341234")
                .phoneNumber("11999995555")
                .mail("julia@test.com")
                .address(address)
                .build();
    }

}
