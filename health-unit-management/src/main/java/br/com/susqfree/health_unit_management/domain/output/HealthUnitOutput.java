package br.com.susqfree.health_unit_management.domain.output;

import br.com.susqfree.health_unit_management.domain.model.HealthUnitType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class HealthUnitOutput {

    private Long id;
    private String name;
    private HealthUnitType type;
    private String phone;
    private String street;
    private String number;
    private String complement;
    private String zipcode;
    private String city;
    private String state;
    private BigDecimal latitude;
    private BigDecimal longitude;

}