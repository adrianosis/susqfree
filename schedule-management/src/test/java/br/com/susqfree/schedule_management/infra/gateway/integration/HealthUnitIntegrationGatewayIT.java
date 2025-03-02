package br.com.susqfree.schedule_management.infra.gateway.integration;

import br.com.susqfree.schedule_management.domain.model.HealthUnit;
import br.com.susqfree.schedule_management.utils.HealthUnitHelper;
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
        "spring.cloud.discovery.client.simple.instances.health-unit-management[0].uri=http://localhost:8181"
})
@ActiveProfiles("test")
@WireMockTest(httpPort = 8181)
public class HealthUnitIntegrationGatewayIT {

    @Autowired
    private HealthUnitIntegrationGateway healthUnitIntegrationGateway;

    @Test
    public void shouldFindDoctorById() {
        // Arrange
        var healthUnit = HealthUnitHelper.createHealthUnitDto();
        long healthUnitId = healthUnit.getId();

        stubFor(get("/health-unit/" + healthUnitId)
                .willReturn(okJson(asJsonString(healthUnit))));

        // Act
        var optionalFoundHealthUnit = healthUnitIntegrationGateway.findById(healthUnitId);

        // Assert
        assertThat(optionalFoundHealthUnit.isPresent()).isTrue();
        optionalFoundHealthUnit.ifPresent(foundHealthUnit -> {
            assertThat(foundHealthUnit).isInstanceOf(HealthUnit.class).isNotNull();
            assertThat(foundHealthUnit.getId()).isEqualTo(healthUnit.getId());
            assertThat(foundHealthUnit.getName()).isEqualTo(healthUnit.getName());
        });
    }

}