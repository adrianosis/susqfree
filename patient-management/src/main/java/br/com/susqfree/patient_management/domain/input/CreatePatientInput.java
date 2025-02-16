package br.com.susqfree.patient_management.domain.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatientInput {

    @NotBlank
    @Size(max = 60)
    private String name;
    @NotBlank
    private String gender;
    @NotNull
    private LocalDate birthDate;
    @NotBlank
    @Size(max = 11)
    private String cpf;
    @NotBlank
    @Size(max = 15)
    private String susNumber;
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
