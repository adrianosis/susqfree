package br.com.susqfree.schedule_management.utils;

import br.com.susqfree.schedule_management.domain.model.HealthUnit;
import br.com.susqfree.schedule_management.infra.gateway.db.mongo.document.HealthUnitDocument;
import br.com.susqfree.schedule_management.infra.gateway.integration.dto.HealthUnitDto;

public class HealthUnitHelper {

    public static HealthUnit createHealthUnit() {
        return HealthUnit.builder()
                .id(1L)
                .name("Health Unit")
                .build();
    }

    public static HealthUnitDocument createHealthUnitDocument() {
        return HealthUnitDocument.builder()
                .id(1L)
                .name("Health Unit")
                .build();
    }

    public static HealthUnitDto createHealthUnitDto() {
        return HealthUnitDto.builder()
                .id(1L)
                .name("Health Unit")
                .build();
    }

}
