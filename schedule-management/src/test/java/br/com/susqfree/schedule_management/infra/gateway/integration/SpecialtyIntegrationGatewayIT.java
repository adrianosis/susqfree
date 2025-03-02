package br.com.susqfree.schedule_management.infra.gateway.integration;

import br.com.susqfree.schedule_management.domain.model.Specialty;
import br.com.susqfree.schedule_management.utils.SpecialtyHelper;
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
public class SpecialtyIntegrationGatewayIT {

    @Autowired
    private SpecialtyIntegrationGateway specialtyIntegrationGateway;

    @Test
    public void shouldFindSpecialtyById() {
        // Arrange
        var specialty = SpecialtyHelper.createSpecialtyDto();
        long specialtyId = specialty.getId();

        stubFor(get("/specialties/" + specialtyId)
                .willReturn(okJson(asJsonString(specialty))));

        // Act
        var optionalFoundSpecialty = specialtyIntegrationGateway.findById(specialtyId);

        // Assert
        assertThat(optionalFoundSpecialty.isPresent()).isTrue();
        optionalFoundSpecialty.ifPresent(foundSpecialty -> {
            assertThat(foundSpecialty).isInstanceOf(Specialty.class).isNotNull();
            assertThat(foundSpecialty.getId()).isEqualTo(specialty.getId());
            assertThat(foundSpecialty.getName()).isEqualTo(specialty.getName());
        });
    }
}