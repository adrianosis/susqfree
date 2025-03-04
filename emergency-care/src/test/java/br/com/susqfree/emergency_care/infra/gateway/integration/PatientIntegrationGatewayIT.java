package br.com.susqfree.emergency_care.infra.gateway.integration;

import br.com.susqfree.emergency_care.config.exception.PatientNotFoundException;
import br.com.susqfree.emergency_care.domain.model.Patient;
import br.com.susqfree.emergency_care.utils.PatientHelper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static br.com.susqfree.emergency_care.utils.JsonHelper.asJsonString;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = {
        "eureka.client.enabled=false",
        "spring.cloud.discovery.client.simple.instances.patient-management[0].uri=http://localhost:8282"
})
@ActiveProfiles("test")
@WireMockTest(httpPort = 8282)
public class PatientIntegrationGatewayIT {

    @Autowired
    private PatientIntegrationGateway patientIntegrationGateway;

    @Test
    public void shouldFindPatientById() {
        var patientDto = PatientHelper.createPatientDto();
        UUID patientId = patientDto.getId();

        stubFor(get(urlEqualTo("/api/patient/" + patientId))
                .willReturn(okJson(asJsonString(patientDto))));

        var optionalPatient = patientIntegrationGateway.findById(patientId);

        assertThat(optionalPatient).isPresent();
        optionalPatient.ifPresent(patient -> {
            assertThat(patient).isInstanceOf(Patient.class).isNotNull();
            assertThat(patient.getId()).isEqualTo(patientDto.getId());
            assertThat(patient.getName()).isEqualTo(patientDto.getName());
            assertThat(patient.getCpf()).isEqualTo(patientDto.getCpf());
            assertThat(patient.getSusNumber()).isEqualTo(patientDto.getSusNumber());
        });
    }


    @Test
    public void shouldReturnEmptyWhenPatientNotFound() {
        UUID patientId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        stubFor(get(urlEqualTo("/api/patient/" + patientId))
                .willReturn(aResponse().withStatus(404)));

        var exception = assertThrows(PatientNotFoundException.class, () -> patientIntegrationGateway.findById(patientId));

        assertThat(exception.getMessage()).isEqualTo("Paciente com ID " + patientId + " não encontrado.");
    }

}
