package br.com.fiap.triage_service.infra.gateway.jpa.entity;

public enum CommonSymptom {
    // Cabeça e Pescoço
    DOR_CABECA("Dor de cabeça", 2),
    RIGIDEZ_NUCA("Rigidez na nuca", 4),
    CONFUSAO_MENTAL("Confusão mental", 5),
    OLHOS_VERMELHOS("Olhos vermelhos", 2),
    PERDA_OLFATO("Perda de olfato", 3),
    PERDA_PALADAR("Perda de paladar", 3),
    COCEIRA("Coceira", 1),

    // Sistema Respiratório
    TOSSE("Tosse", 2),
    FALTA_AR("Falta de ar", 5),
    SECRECAO_NASAL("Secreção nasal", 1),

    // Sistema Cardiovascular
    DOR_PEITO("Dor no peito", 5),
    PALPITACOES("Palpitações", 4),
    FRAQUEZA("Fraqueza", 3),

    // Sistema Gastrointestinal
    DOR_ABDOMINAL("Dor abdominal", 3),
    NAUSEA_VOMITO("Náusea/Vômito", 3),
    DIARREIA("Diarreia", 3),

    // Sistema Musculoesquelético
    DOR_MUSCULAR("Dor muscular", 2),
    FADIGA("Fadiga", 2),
    INCHACO("Inchaço", 3),
    RIGIDEZ_ARTICULACOES("Rigidez nas articulações", 3),

    // Sintomas Gerais
    FEBRE("Febre", 2),
    SUOR_NOTURNO("Suor noturno", 2),
    PERDA_PESO("Perda de peso", 3),
    SANGRAMENTO("Sangramento", 5),
    ERUPCAO_CUTANEA("Erupção cutânea", 2);

    private final String description;
    private final int severityLevel;

    CommonSymptom(String description, int severityLevel) {
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