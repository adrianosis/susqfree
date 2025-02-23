package br.com.fiap.triage_service.infra.gateway.jpa.entity;

import lombok.Getter;

@Getter
public enum PriorityCode {
    R("RED", "Emergência - Atendimento imediato necessário. Condição com risco de vida."),
    Y("YELLOW", "Urgência - Necessita de atenção urgente. Condição grave."),
    G("GREEN", "Prioritário - Requer atenção médica, mas não é grave."),
    B("BLUE", "Não prioritário - Pode aguardar atendimento.");

    private final String code;
    private final String diagnosis;

    PriorityCode(String code, String diagnosis) {
        this.code = code;
        this.diagnosis = diagnosis;
    }
}
