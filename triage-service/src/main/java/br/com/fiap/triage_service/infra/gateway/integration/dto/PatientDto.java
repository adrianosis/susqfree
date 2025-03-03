package br.com.fiap.triage_service.infra.gateway.integration.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
public class PatientDto {
    private UUID id;
    private String name;
    private String gender;
    private String cpf;
    private String susNumber;
}
