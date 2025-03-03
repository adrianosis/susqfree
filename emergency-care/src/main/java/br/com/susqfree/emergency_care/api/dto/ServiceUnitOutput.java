package br.com.susqfree.emergency_care.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceUnitOutput {
    private Long id;
    private String serviceType;
    private Integer capacity;
    private Long unitId;
}
