package br.com.fiap.triage_service.domain.output;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TriagePriorityOutput {

    private String priority;
    private String diagnosis;

}