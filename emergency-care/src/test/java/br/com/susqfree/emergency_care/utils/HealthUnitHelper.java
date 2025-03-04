package br.com.susqfree.emergency_care.utils;

import br.com.susqfree.emergency_care.domain.model.HealthUnit;
import br.com.susqfree.emergency_care.infra.gateway.integration.dto.HealthUnitDto;

public class HealthUnitHelper {

    public static HealthUnit createHealthUnit() {
        return new HealthUnit(
                1L,
                "Hospital Municipal"
        );
    }

    public static HealthUnitDto createHealthUnitDto() {
        return new HealthUnitDto(
                1L,
                "Hospital Municipal"
        );
    }
}
