package br.com.fiap.triage_service.infra.gateway.integration.client;

import br.com.fiap.triage_service.infra.gateway.integration.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(value = "patient-management", path = "/api/patient")
public interface PatientClient {
    @GetMapping(value = "/{patientId}")
    public PatientDto findById(@PathVariable UUID patientId);
}
