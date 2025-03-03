package br.com.susqfree.emergency_care.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceUnit {

    private final Long id;
    private final String serviceType;
    private final Integer capacity;
    private final Long unitId;
}
