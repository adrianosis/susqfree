package br.com.susqfree.emergency_care.infra.gateway.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

    private UUID id;
    private String name;
    private String cpf;
    private String susNumber;

}


