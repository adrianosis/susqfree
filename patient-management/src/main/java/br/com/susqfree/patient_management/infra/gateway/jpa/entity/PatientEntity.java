package br.com.susqfree.patient_management.infra.gateway.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient")
public class PatientEntity {

    @Id
    private UUID id;
    private String name;
    private String gender;
    private String cpf;
    @Column(name = "sus_number")
    private String susNumber;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String mail;

    @Embedded
    private AddressEntity address;

}
