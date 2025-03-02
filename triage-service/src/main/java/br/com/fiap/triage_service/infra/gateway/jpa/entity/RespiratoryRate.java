package br.com.fiap.triage_service.infra.gateway.jpa.entity;

public enum RespiratoryRate {
    NORMAL("Normal", 1),
    DISPNEIA_LEVE("Dispneia Leve", 2),
    DISPNEIA_MODERADA("Dispneia Moderada", 4),
    DISPNEIA_SEVERA("Dispneia Severa", 5);

    private final String description;
    private final int severityLevel;

    RespiratoryRate(String description, int severityLevel) {
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