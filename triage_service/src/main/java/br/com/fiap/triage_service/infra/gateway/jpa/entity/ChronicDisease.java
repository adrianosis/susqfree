package br.com.fiap.triage_service.infra.gateway.jpa.entity;

public enum ChronicDisease {
    NENHUMA("Nenhuma", 0),
    HIPERTENSAO("Hipertensão", 3),
    DIABETES("Diabetes", 3),
    DOENCA_CARDIACA("Doença cardíaca", 4),
    DOENCA_RESPIRATORIA("Doença respiratória", 4),
    DOENCA_RENAL("Doença renal", 4),
    IMUNOSSUPRESSAO("Imunossupressão", 5);

    private final String descricao;
    private final int severityLevel;

    ChronicDisease(String descricao, int severityLevel) {
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