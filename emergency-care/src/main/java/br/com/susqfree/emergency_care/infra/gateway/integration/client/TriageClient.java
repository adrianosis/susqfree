package br.com.susqfree.emergency_care.infra.gateway.integration.client;

import br.com.susqfree.emergency_care.config.security.OAuth2FeignRequestInterceptor;
import br.com.susqfree.emergency_care.infra.gateway.integration.dto.TriageInputDto;
import br.com.susqfree.emergency_care.infra.gateway.integration.dto.TriagePriorityOutputDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "triage-service", path = "/triage", configuration = OAuth2FeignRequestInterceptor.class)
public interface TriageClient {

    @PostMapping(value = "/process-triage")
    TriagePriorityOutputDto processTriage(@RequestBody TriageInputDto triageInput);
}
