package br.com.susqfree.health_unit_management.domain.mapper;

import br.com.susqfree.health_unit_management.domain.input.HealthUnitInput;
import br.com.susqfree.health_unit_management.domain.model.HealthUnit;
import br.com.susqfree.health_unit_management.domain.output.HealthUnitOutput;

import java.math.BigDecimal;

public class HealthUnitMapper {

    public static HealthUnitOutput toOutput(HealthUnit healthUnit) {
        return HealthUnitOutput.builder()
                .id(healthUnit.getId())
                .name(healthUnit.getName())
                .type(healthUnit.getType())
                .phone(healthUnit.getPhone())
                .street(healthUnit.getStreet())
                .number(healthUnit.getNumber())
                .complement(healthUnit.getComplement())
                .zipcode(healthUnit.getZipcode())
                .city(healthUnit.getCity())
                .state(healthUnit.getState())
                .latitude(healthUnit.getLatitude())
                .longitude(healthUnit.getLongitude())
                .build();
    }
    public static HealthUnit toDomain(HealthUnitInput healthUnit) {
        return HealthUnit.builder()
                .name(healthUnit.getName())
                .type(healthUnit.getType())
                .phone(healthUnit.getPhone())
                .street(healthUnit.getStreet())
                .number(healthUnit.getNumber())
                .complement(healthUnit.getComplement())
                .zipcode(healthUnit.getZipcode())
                .city(healthUnit.getCity())
                .state(healthUnit.getState())
                .latitude(healthUnit.getLatitude())
                .longitude(healthUnit.getLongitude())
                .build();
    }

}
