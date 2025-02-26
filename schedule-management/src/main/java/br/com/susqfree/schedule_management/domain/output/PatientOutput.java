package br.com.susqfree.schedule_management.domain.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientOutput {

    private UUID id;
    private String name;
    private String cpf;
    private String susNumber;

}
