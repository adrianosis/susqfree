package br.com.fiap.triage_service.infra.gateway.jpa.entity;

public enum ChronicDisease {
    NENHUMA("Nenhuma", 0),
    HIPERTENSAO("Hipertensão", 3),
    DIABETES("Diabetes", 3),
    DOENCA_CARDIACA("Doença cardíaca", 4),
    DOENCA_RESPIRATORIA("Doença respiratória", 4),
    DOENCA_RENAL("Doença renal", 4),
    IMUNOSSUPRESSAO("Imunossupressão", 5);

    private final String description;
    private final int severityLevel;

    ChronicDisease(String description, int severityLevel) {
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