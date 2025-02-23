package br.com.fiap.triage_service.infra.gateway.jpa.entity;

public enum Pregnancy {
    NAO_APLICAVEL("Não aplicável", 0),
    SIM("Sim", 2),
    NAO("Não", 0),
    NAO_SEI("Não sei", 0);

    private final String descricao;
    private final int severityLevel;

    Pregnancy(String descricao, int severityLevel) {
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