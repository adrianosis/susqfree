package br.com.susqfree.patient.infra.gateway.jpa.entity;

import br.com.susqfree.patient.domain.model.Patient;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient")
public class PatientEntity {

    private long id;
    private String name;
    private String cpf;

}
