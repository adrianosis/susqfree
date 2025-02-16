package br.com.susqfree.patient_management.domain.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePatientInput {

    @NotBlank
    @Size(max = 60)
    private String name;
    @NotBlank
    private String gender;
    @NotBlank
    @Size(max = 15)
    private String phoneNumber;
    private String mail;

    @NotBlank
    @Size(max = 60)
    private String street;
    @NotBlank
    @Size(max = 10)
    private String number;
    @Size(max = 60)
    private String complement;
    @NotBlank
    @Size(max = 30)
    private String district;
    @NotBlank
    @Size(max = 30)
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String postalCode;
    private Double longitude;
    private Double latitude;

}
