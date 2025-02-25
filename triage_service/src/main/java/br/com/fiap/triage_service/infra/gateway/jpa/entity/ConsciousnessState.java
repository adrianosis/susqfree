package br.com.fiap.triage_service.infra.gateway.jpa.entity;

public enum ConsciousnessState {
    ALTERADO("Alterado", 1),
    NORMAL("Normal", 0),
    CONFUSAO("Confusão Mental", 4),
    INCONSCIENTE("Inconsciente", 5);

    private final String description;
    private final int severityLevel;

    ConsciousnessState(String description, int severityLevel) {
        this.description = description;
        this.severityLevel = severityLevel;
    }

    public String getDescription() {
        return description;
    }

    public int getSeverityLevel() {
        return severityLevel;
    }
}