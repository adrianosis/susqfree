package br.com.susqfree.health_unit_management.domain.input;

import br.com.susqfree.health_unit_management.domain.model.HealthUnitType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class HealthUnitInput {

    @NotBlank
    @Size(max = 60)
    private String name;

    @NotNull
    private HealthUnitType type;

    @Size(max = 16)
    private String phone;

    @NotBlank
    @Size(max = 60)
    private String street;

    @NotBlank
    @Size(max = 10)
    private String number;

    @Size(max = 30)
    private String complement;

    @NotBlank
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Zipcode must be in the format XXXXX-XXX")
    private String zipcode;

    @NotBlank
    @Size(max = 30)
    private String city;

    @NotBlank
    @Size(min = 2, max = 2, message = "State must have exactly 2 characters")
    private String state;

    @DecimalMin(value = "-90.000000", message = "Latitude must be valid")
    @DecimalMax(value = "90.000000", message = "Latitude must be valid")
    private BigDecimal latitude;

    @DecimalMin(value = "-180.000000", message = "Longitude must be valid")
    @DecimalMax(value = "180.000000", message = "Longitude must be valid")
    private BigDecimal longitude;
}
