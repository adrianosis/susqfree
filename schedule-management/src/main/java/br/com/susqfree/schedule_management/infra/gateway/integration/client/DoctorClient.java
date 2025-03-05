package br.com.susqfree.schedule_management.infra.gateway.integration.client;

import br.com.susqfree.schedule_management.infra.config.security.OAuth2FeignRequestInterceptor;
import br.com.susqfree.schedule_management.infra.gateway.integration.dto.DoctorDto;
import br.com.susqfree.schedule_management.infra.gateway.integration.dto.SpecialtyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "doctor-management", path = "/", configuration = OAuth2FeignRequestInterceptor.class)
public interface DoctorClient {

    @GetMapping(value = "/doctors/{doctorId}")
    DoctorDto findDoctorById(@PathVariable long doctorId);

    @GetMapping(value = "/specialties/{specialtyId}")
    SpecialtyDto findSpecialtyById(@PathVariable long specialtyId);

}
