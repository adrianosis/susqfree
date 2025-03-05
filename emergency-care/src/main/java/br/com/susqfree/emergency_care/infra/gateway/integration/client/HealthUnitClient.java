package br.com.susqfree.emergency_care.infra.gateway.integration.client;

import br.com.susqfree.emergency_care.config.security.OAuth2FeignRequestInterceptor;
import br.com.susqfree.emergency_care.infra.gateway.integration.dto.HealthUnitDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "health-unit-management", path = "/health-unit", configuration = OAuth2FeignRequestInterceptor.class)
public interface HealthUnitClient {

    @GetMapping(value = "/{healthUnitId}")
    HealthUnitDto findById(@PathVariable long healthUnitId);

}
