package br.com.fiap.triage_service.infra.gateway.jpa.entity;

public enum ChronicDisease {
    NENHUMA("Nenhuma", 0),
    HIPERTENSAO("Hipertensão", 1),
    DIABETES("Diabetes", 1),
    DOENCA_CARDIACA("Doença cardíaca", 1),
    DOENCA_RESPIRATORIA("Doença respiratória", 1),
    DOENCA_RENAL("Doença renal", 1),
    IMUNOSSUPRESSAO("Imunossupressão", 1);

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