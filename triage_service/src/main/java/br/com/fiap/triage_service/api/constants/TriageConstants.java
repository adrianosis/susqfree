package br.com.fiap.triage_service.api.constants;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TriageConstants {
    public static final Map<String, Object> TRIAGE_QUESTIONS = new LinkedHashMap<>() {{
        put("1. Identificação do Atendimento", "ID gerado pelo sistema");
        put("2. Queixa Principal", new LinkedHashMap<String, Object>() {{
            put("Motivo da Consulta", "Campo livre");
            put("Duração dos Sintomas", List.of("Menos de 24 horas", "1 a 3 dias", "4 a 7 dias", "Mais de uma semana"));
        }});
        put("3. Sinais Vitais", new LinkedHashMap<String, Object>() {{
            put("Temperatura Corporal (°C)", "Valor numérico");
            put("Pressão Arterial", List.of("Normal", "Elevada", "Hipertensão", "Hipotensão"));
            put("Frequência Cardíaca", List.of("Normal", "Taquicardia", "Bradicardia"));
            put("Frequência Respiratória", List.of("Normal", "Dispneia Leve (falta de ar leve)", "Dispneia Moderada", "Dispneia Severa"));
            put("Estado de Consciência", List.of("Alerta", "Confusão Mental", "Inconsciente"));
        }});
        put("4. Sintomas Comuns", new LinkedHashMap<String, Object>() {{
            put("Cabeça e Pescoço", List.of("Dor de cabeça", "Rigidez na nuca", "Confusão mental", "Olhos vermelhos", "Perda de olfato", "Perda de paladar", "Coceira"));
            put("Sistema Respiratório", List.of("Tosse", "Falta de ar", "Secreção nasal", "Dispneia"));
            put("Sistema Cardiovascular", List.of("Dor no peito", "Palpitações", "Fraqueza"));
            put("Sistema Gastrointestinal", List.of("Dor abdominal", "Náusea/Vômito", "Diarreia"));
            put("Sistema Musculoesquelético", List.of("Dor muscular", "Fadiga", "Inchaço", "Rigidez nas articulações"));
            put("Sintomas Gerais", List.of("Febre", "Suor noturno", "Perda de peso", "Sangramento", "Erupção cutânea"));
        }});
        put("5. Dor e Sangramento", new LinkedHashMap<String, Object>() {{
            put("Intensidade da Dor", List.of("Sem dor", "Leve", "Moderada", "Intensa", "Insuportável"));
            put("Sangramento presente?", List.of("Sim", "Não"));
            put("Local do Sangramento", List.of("Não aplicável", "Boca", "Nariz", "Ouvido", "Retal", "Urinário", "Outro (Especificar)"));
        }});
        put("6. Histórico Clínico", new LinkedHashMap<String, Object>() {{
            put("Doenças Crônicas", List.of("Diabetes", "Hipertensão", "Doenças cardiovasculares", "Doenças respiratórias crônicas", "Insuficiência renal", "Outras (Especificar)"));
            put("Uso de Medicação Contínua", List.of("Sim (Especificar)", "Não"));
            put("Gravidez", List.of("Não aplicável", "Sim", "Não", "Não sei"));
            put("Alergias", List.of("Sim (Especificar)", "Não"));
        }});
    }};
}