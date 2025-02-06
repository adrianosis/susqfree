package br.com.susqfree.patient_management.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Patient {

    private long id;
    private String name;
    private String cpf;


}
