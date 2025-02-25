package br.com.fiap.triage_service.infra.gateway.jpa.entity;

public enum BleedingPresence {
    SIM("Sim", 4),
    NAO("Não", 0);

    private final String description;
    private final int severityLevel;

    BleedingPresence(String description, int severityLevel) {
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