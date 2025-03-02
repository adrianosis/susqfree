package br.com.fiap.triage_service.infra.gateway.integration.dto;

import java.time.LocalDate;
import java.util.UUID;

public class PatientDto {
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
