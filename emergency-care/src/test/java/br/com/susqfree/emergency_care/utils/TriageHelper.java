package br.com.susqfree.emergency_care.utils;

import br.com.susqfree.emergency_care.domain.model.TriageInput;
import br.com.susqfree.emergency_care.infra.gateway.integration.dto.TriagePriorityOutputDto;

import java.util.List;
import java.util.UUID;

public class TriageHelper {

    public static TriageInput createTriageInput() {
        return new TriageInput(
                UUID.randomUUID(),
                "Dor intensa no peito",
                "MENOS_DE_UM_DIA",
                39.5,
                "NORMAL",
                "TAQUICARDIA",
                "NORMAL",
                "NORMAL",
                List.of("DOR_PEITO", "FALTA_AR"),
                "INTENSA",
                "NAO",
                List.of("HIPERTENSAO"),
                List.of(),
                "NAO",
                List.of()
        );
    }

    public static TriagePriorityOutputDto createTriagePriorityDto() {
        return new TriagePriorityOutputDto("R", "Emergência - Atendimento imediato necessário.");
    }
}
