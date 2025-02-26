package br.com.susqfree.schedule_management.domain.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthUnitOutput {

    private Long id;
    private String name;

}
