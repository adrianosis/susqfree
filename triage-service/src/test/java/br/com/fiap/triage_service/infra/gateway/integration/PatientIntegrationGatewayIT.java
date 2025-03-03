package br.com.fiap.triage_service.infra.gateway.integration;

import br.com.fiap.triage_service.helper.PatientHelper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static br.com.fiap.triage_service.helper.TriageHelper.asJsonString;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "eureka.client.enabled=false",
        "spring.cloud.discovery.client.simple.instances.patient-management[0].uri=http://localhost:8181"
})
@ActiveProfiles("test")
@WireMockTest(httpPort = 8181)
public class PatientIntegrationGatewayIT {

    @Autowired
    private PatientIntegrationGateway patientIntegrationGateway;

    @Test
    public void shouldFindPatientById() {
        var patient = PatientHelper.createPatientDto();
        UUID patientId = patient.getId();

        stubFor(get("/api/patient/" + patientId)
                .willReturn(okJson(asJsonString(patient))));

        Boolean result = patientIntegrationGateway.existsPatient(patientId);

        assertThat(result.equals(Boolean.TRUE)).isTrue();
    }

}