package br.com.susqfree.schedule_management.infra.gateway.integration;

import br.com.susqfree.schedule_management.domain.model.HealthUnit;
import br.com.susqfree.schedule_management.infra.gateway.integration.client.HealthUnitClient;
import br.com.susqfree.schedule_management.infra.gateway.integration.mapper.HealthUnitDtoMapper;
import br.com.susqfree.schedule_management.utils.HealthUnitHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class HealthUnitIntegrationGatewayTest {

    private HealthUnitIntegrationGateway healthUnitIntegrationGateway;

    @Mock
    private HealthUnitClient healthUnitClient;
    AutoCloseable openMocks;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        healthUnitIntegrationGateway = new HealthUnitIntegrationGateway(healthUnitClient, new HealthUnitDtoMapper());
    }

    @Test
    public void shouldFindDoctorById() {
        // Arrange
        var healthUnit = HealthUnitHelper.createHealthUnitDto();
        long healthUnitId = healthUnit.getId();

        when(healthUnitClient.findById(anyLong())).thenReturn(healthUnit);

        // Act
        var optionalFoundHealthUnit = healthUnitIntegrationGateway.findById(healthUnitId);

        // Assert
        verify(healthUnitClient, times(1)).findById(anyLong());

        assertThat(optionalFoundHealthUnit.isPresent()).isTrue();
        optionalFoundHealthUnit.ifPresent(foundHealthUnit -> {
            assertThat(foundHealthUnit).isInstanceOf(HealthUnit.class).isNotNull();
            assertThat(foundHealthUnit.getId()).isEqualTo(healthUnit.getId());
            assertThat(foundHealthUnit.getName()).isEqualTo(healthUnit.getName());
        });
    }

}