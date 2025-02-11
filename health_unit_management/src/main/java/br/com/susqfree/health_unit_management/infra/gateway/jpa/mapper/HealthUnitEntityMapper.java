package br.com.susqfree.health_unit_management.infra.gateway.jpa.mapper;

import br.com.susqfree.health_unit_management.domain.model.HealthUnit;
import br.com.susqfree.health_unit_management.infra.gateway.jpa.entity.HealthUnitEntity;

public class HealthUnitEntityMapper {
    public static HealthUnitEntity toEntity(HealthUnit healthUnit) {
        return HealthUnitEntity.builder()
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

    public static HealthUnit toDomain(HealthUnitEntity healthUnitEntity) {
        return HealthUnit.builder()
                .id(healthUnitEntity.getId())
                .name(healthUnitEntity.getName())
                .type(healthUnitEntity.getType())
                .phone(healthUnitEntity.getPhone())
                .street(healthUnitEntity.getStreet())
                .number(healthUnitEntity.getNumber())
                .complement(healthUnitEntity.getComplement())
                .zipcode(healthUnitEntity.getZipcode())
                .city(healthUnitEntity.getCity())
                .state(healthUnitEntity.getState())
                .latitude(healthUnitEntity.getLatitude())
                .longitude(healthUnitEntity.getLongitude())
                .build();
    }
}
