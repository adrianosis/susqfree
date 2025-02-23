package br.com.fiap.triage_service.infra.gateway.jpa.entity;

public enum HeartRate {
    NORMAL("Normal", 0),
    TAQUICARDIA("Taquicardia", 3),
    BRADICARDIA("Bradicardia", 3);

    private final String descricao;
    private final int severityLevel;

    HeartRate(String descricao, int severityLevel) {
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
