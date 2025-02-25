package br.com.fiap.triage_service.infra.gateway.jpa.entity;

public enum BloodPressure {
    NORMAL("Normal", 0),
    HIPOTENSAO("Hipotensão", 3),
    HIPERTENSAO("Hipertensão", 3);

    private final String description;
    private final int severityLevel;

    BloodPressure(String description, int severityLevel) {
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
