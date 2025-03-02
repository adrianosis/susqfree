package br.com.susqfree.schedule_management.infra.gateway.integration;

import br.com.susqfree.schedule_management.domain.model.Patient;
import br.com.susqfree.schedule_management.utils.PatientHelper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static br.com.susqfree.schedule_management.utils.JsonHelper.asJsonString;
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
        // Arrange
        var patient = PatientHelper.createPatientDto();
        UUID patientId = patient.getId();

        stubFor(get("/api/patient/" + patientId)
                .willReturn(okJson(asJsonString(patient))));

        // Act
        var optionalFoundPatient = patientIntegrationGateway.findById(patientId);

        // Assert
        assertThat(optionalFoundPatient.isPresent()).isTrue();
        optionalFoundPatient.ifPresent(foundPatient -> {
            assertThat(foundPatient).isInstanceOf(Patient.class).isNotNull();
            assertThat(foundPatient.getId()).isEqualTo(patient.getId());
            assertThat(foundPatient.getName()).isEqualTo(patient.getName());
            assertThat(foundPatient.getCpf()).isEqualTo(patient.getCpf());
            assertThat(foundPatient.getSusNumber()).isEqualTo(patient.getSusNumber());
        });
    }

}