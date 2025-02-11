package br.com.susqfree.health_unit_management.utils;

import br.com.susqfree.health_unit_management.domain.input.HealthUnitInput;
import br.com.susqfree.health_unit_management.domain.model.HealthUnit;
import br.com.susqfree.health_unit_management.domain.model.HealthUnitType;
import br.com.susqfree.health_unit_management.domain.output.HealthUnitOutput;
import br.com.susqfree.health_unit_management.infra.gateway.jpa.entity.HealthUnitEntity;

import java.math.BigDecimal;

public class HealthUnitHelper {

    public static HealthUnitInput createHealthUnitInput() {
        return HealthUnitInput.builder()
                .name("Unidade de Saude Central")
                .type(HealthUnitType.HOSPITAL)
                .phone("(21) 98450-6545")
                .street("Rua Principal")
                .number("123")
                .complement("Proximo ao shopping")
                .zipcode("12345-678")
                .city("Rio de Janeiro")
                .state("RJ")
                .latitude(new BigDecimal("-22.9068"))
                .longitude(new BigDecimal("-43.1729"))
                .build();
    }

    public static HealthUnitOutput createHealthUnitOutput(Long id) {
        return HealthUnitOutput.builder()
                .id(id)
                .name("Unidade de Saude Central")
                .type(HealthUnitType.HOSPITAL)
                .phone("(21) 98450-6545")
                .street("Rua Principal")
                .number("123")
                .complement("Proximo ao shopping")
                .zipcode("12345-678")
                .city("Rio de Janeiro")
                .state("RJ")
                .latitude(new BigDecimal("-22.9068"))
                .longitude(new BigDecimal("-43.1729"))
                .build();
    }

    public static HealthUnitEntity createHealthUnitEntity(Long id) {
        return HealthUnitEntity.builder()
                .id(id)
                .name("Unidade de Saude Central")
                .type(HealthUnitType.HOSPITAL)
                .phone("(21) 98450-6545")
                .street("Rua Principal")
                .number("123")
                .complement("Proximo ao shopping")
                .zipcode("12345-678")
                .city("Rio de Janeiro")
                .state("RJ")
                .latitude(new BigDecimal("-22.9068"))
                .longitude(new BigDecimal("-43.1729"))
                .build();
    }

    public static HealthUnit createHealthUnit(Long id) {
        return HealthUnit.builder()
                .id(id)
                .name("Unidade de Saude Central")
                .type(HealthUnitType.HOSPITAL)
                .phone("(21) 98450-6545")
                .street("Rua Principal")
                .number("123")
                .complement("Proximo ao shopping")
                .zipcode("12345-678")
                .city("Rio de Janeiro")
                .state("RJ")
                .latitude(new BigDecimal("-22.9068"))
                .longitude(new BigDecimal("-43.1729"))
                .build();
    }

}
