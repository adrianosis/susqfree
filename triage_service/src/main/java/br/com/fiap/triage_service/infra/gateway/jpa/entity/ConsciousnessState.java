package br.com.fiap.triage_service.infra.gateway.jpa.entity;

public enum ConsciousnessState {
    ALTERADO("Alterado", 1),
    NORMAL("Normal", 0),
    CONFUSAO("Confusão Mental", 4),
    INCONSCIENTE("Inconsciente", 5);

    private final String descricao;
    private final int severityLevel;

    ConsciousnessState(String descricao, int severityLevel) {
        this.descricao = descricao;
        this.severityLevel = severityLevel;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getSeverityLevel() {
        return severityLevel;
    }
}