package br.com.fiap.triage_service.infra.gateway.jpa.entity;

import lombok.Getter;

@Getter
public enum SymptomDuration {
    MENOS_DE_UM_DIA("Menos de um dia"),
    UM_A_TRES_DIAS("1 a 3 dias"),
    QUATRO_A_SETE_DIAS("4 a 7 dias"),
    MAIS_DE_UMA_SEMANA("Mais de uma semana"),
    CRONICO("Crônico");

    private final String description;

    SymptomDuration(String description) {
        this.description = description;
    }
}
