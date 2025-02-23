package br.com.fiap.triage_service.infra.gateway.jpa.entity;

public enum RespiratoryRate {
    NORMAL("Normal", 1),
    DISPNEIA_LEVE("Dispneia Leve", 2),
    DISPNEIA_MODERADA("Dispneia Moderada", 4),
    DISPNEIA_SEVERA("Dispneia Severa", 5);

    private final String descricao;
    private final int severityLevel;

    RespiratoryRate(String descricao, int severityLevel) {
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