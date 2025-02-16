package br.com.susqfree.patient_management.infra.gateway.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AddressEntity {

    @NotNull
    @Column(length = 60)
    private String street;
    @NotNull
    @Column(length = 10)
    private String number;
    @Column(length = 60)
    private String complement;
    @NotNull
    @Column(length = 30)
    private String district;
    @NotNull
    @Column(length = 30)
    private String city;
    @NotNull
    @Column(length = 2)
    private String state;
    @NotNull
    @Column(name = "postal_code", length = 8)
    private String postalCode;
    private Double longitude;
    private Double latitude;

}
