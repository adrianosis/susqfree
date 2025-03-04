package br.com.susqfree.emergency_care.infra.gateway.integration;

import br.com.susqfree.emergency_care.config.exception.HealthUnitNotFoundException;
import br.com.susqfree.emergency_care.domain.model.HealthUnit;
import br.com.susqfree.emergency_care.utils.JsonHelper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void shouldFindHealthUnitById() {
        long healthUnitId = 1L;
        var healthUnitDto = new HealthUnit(healthUnitId, "Central Health Unit");

        stubFor(get(urlEqualTo("/health-unit/" + healthUnitId))
                .willReturn(okJson(JsonHelper.asJsonString(healthUnitDto))));

        var optionalHealthUnit = healthUnitIntegrationGateway.findById(healthUnitId);

        assertThat(optionalHealthUnit).isPresent();
        optionalHealthUnit.ifPresent(foundUnit -> {
            assertThat(foundUnit).isInstanceOf(HealthUnit.class);
            assertThat(foundUnit.getId()).isEqualTo(healthUnitDto.getId());
            assertThat(foundUnit.getName()).isEqualTo(healthUnitDto.getName());
        });
    }

    @Test
    public void shouldThrowExceptionWhenHealthUnitNotFound() {
        long healthUnitId = 99L;

        stubFor(get(urlEqualTo("/health-unit/" + healthUnitId))
                .willReturn(aResponse().withStatus(404)));

        var exception = assertThrows(HealthUnitNotFoundException.class, () -> healthUnitIntegrationGateway.findById(healthUnitId));

        assertThat(exception.getMessage()).isEqualTo("Unidade de saúde com ID " + healthUnitId + " não encontrada.");
    }
}
