package br.com.susqfree.schedule_management.infra.gateway.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {

    private Long id;
    private String name;
    private String crm;

}
