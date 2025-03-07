package br.com.susqfree.schedule_management.infra.gateway.integration.client;

import br.com.susqfree.schedule_management.infra.config.security.OAuth2FeignRequestInterceptor;
import br.com.susqfree.schedule_management.infra.gateway.integration.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(value = "patient-management", path = "/api/patient", configuration = OAuth2FeignRequestInterceptor.class)
public interface PatientClient {

    @GetMapping(value = "/{patientId}")
    PatientDto findById(@PathVariable UUID patientId);

}
