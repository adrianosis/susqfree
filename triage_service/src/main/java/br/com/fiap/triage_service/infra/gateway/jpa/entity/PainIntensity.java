package br.com.fiap.triage_service.infra.gateway.jpa.entity;

public enum PainIntensity {
    SEM_DOR("Sem dor", 0),
    LEVE("Leve", 1),
    MODERADA("Moderada", 3),
    INTENSA("Intensa", 4),
    INSUPORTAVEL("Insuportável", 5);

    private final String descricao;
    private final int severityLevel;

    PainIntensity(String descricao, int severityLevel) {
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