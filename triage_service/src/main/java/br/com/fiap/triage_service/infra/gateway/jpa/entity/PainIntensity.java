package br.com.fiap.triage_service.infra.gateway.jpa.entity;

public enum PainIntensity {
    SEM_DOR("Sem dor", 0),
    LEVE("Leve", 1),
    MODERADA("Moderada", 3),
    INTENSA("Intensa", 4),
    INSUPORTAVEL("Insuportável", 5);

    private final String description;
    private final int severityLevel;

    PainIntensity(String description, int severityLevel) {
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