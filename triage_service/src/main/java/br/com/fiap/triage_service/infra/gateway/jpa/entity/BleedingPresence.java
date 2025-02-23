package br.com.fiap.triage_service.infra.gateway.jpa.entity;

public enum BleedingPresence {
    SIM("Sim", 5),
    NAO("Não", 0);

    private final String descricao;
    private final int severityLevel;

    BleedingPresence(String descricao, int severityLevel) {
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