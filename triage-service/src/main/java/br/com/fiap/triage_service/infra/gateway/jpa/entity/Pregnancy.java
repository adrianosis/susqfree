package br.com.fiap.triage_service.infra.gateway.jpa.entity;

public enum Pregnancy {
    NAO_APLICAVEL("Não aplicável", 0),
    SIM("Sim", 2),
    NAO("Não", 0),
    NAO_SEI("Não sei", 0);

    private final String description;
    private final int severityLevel;

    Pregnancy(String description, int severityLevel) {
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