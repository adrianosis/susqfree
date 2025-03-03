package br.com.susqfree.emergency_care.infra.gateway.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthUnitDto {

    private Long id;
    private String name;

}
