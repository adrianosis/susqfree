package br.com.susqfree.emergency_care.infra.gateway.integration.dto;

public record TriagePriorityOutputDto(
        String priority,
        String diagnosis
) {}
