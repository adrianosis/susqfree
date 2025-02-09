package br.com.susqfree.health_unit_management.domain.input;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class HealthUnitInput {
    private String name;
    private String type;
    private Integer capacity;
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
