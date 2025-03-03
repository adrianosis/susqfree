package br.com.susqfree.emergency_care.infra.gateway.integration.client;

import br.com.susqfree.emergency_care.infra.gateway.integration.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(value = "patient-management", path = "/api/patient")
public interface PatientClient {

    @GetMapping(value = "/{patientId}")
    PatientDto findById(@PathVariable UUID patientId);

}