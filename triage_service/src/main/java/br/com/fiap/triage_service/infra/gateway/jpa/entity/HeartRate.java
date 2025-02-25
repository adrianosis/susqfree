package br.com.fiap.triage_service.infra.gateway.jpa.entity;

public enum HeartRate {
    NORMAL("Normal", 0),
    TAQUICARDIA("Taquicardia", 3),
    BRADICARDIA("Bradicardia", 3);

    private final String description;
    private final int severityLevel;

    HeartRate(String description, int severityLevel) {
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
