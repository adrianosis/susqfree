package br.com.susqfree.emergency_care.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceUnitInput {
    private String serviceType;
    private Integer capacity;
    private Long unitId;
}
