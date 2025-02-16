package br.com.susqfree.patient_management.domain.output;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
public class PatientOutput {

    private UUID id;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private String cpf;
    private String susNumber;
    private String phoneNumber;
    private String mail;

    private String street;
    private String number;
    private String complement;
    private String district;
    private String city;
    private String state;
    private String postalCode;
    private Double longitude;
    private Double latitude;

}
