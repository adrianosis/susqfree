package br.com.susqfree.schedule_management.infra.gateway.integration;

import br.com.susqfree.schedule_management.domain.model.Doctor;
import br.com.susqfree.schedule_management.utils.DoctorHelper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static br.com.susqfree.schedule_management.utils.JsonHelper.asJsonString;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "eureka.client.enabled=false",
        "spring.cloud.discovery.client.simple.instances.doctor-management[0].uri=http://localhost:8181"
})
@ActiveProfiles("test")
@WireMockTest(httpPort = 8181)
public class DoctorIntegrationGatewayIT {

    @Autowired
    private DoctorIntegrationGateway doctorIntegrationGateway;

    @Test
    public void shouldFindDoctorById() {
        // Arrange
        var doctor = DoctorHelper.createDoctorDto();
        long doctorId = doctor.getId();

        stubFor(get("/doctors/" + doctorId)
                .willReturn(okJson(asJsonString(doctor))));

        // Act
        var optionalFoundDoctor = doctorIntegrationGateway.findById(doctorId);

        // Assert
        assertThat(optionalFoundDoctor.isPresent()).isTrue();
        optionalFoundDoctor.ifPresent(foundDoctor -> {
            assertThat(foundDoctor).isInstanceOf(Doctor.class).isNotNull();
            assertThat(foundDoctor.getId()).isEqualTo(doctor.getId());
            assertThat(foundDoctor.getName()).isEqualTo(doctor.getName());
            assertThat(foundDoctor.getCrm()).isEqualTo(doctor.getCrm());
        });
    }

}