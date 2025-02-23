package br.com.fiap.triage_service.infra.gateway.jpa.entity;

public enum BloodPressure {
    NORMAL("Normal", 0),
    HIPOTENSAO("Hipotensão", 3),
    HIPERTENSAO("Hipertensão", 3);

    private final String descricao;
    private final int severityLevel;

    BloodPressure(String descricao, int severityLevel) {
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
