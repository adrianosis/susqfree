package br.com.susqfree.schedule_management.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    private UUID id;
    private String name;
    private String cpf;
    private String susNumber;

}
