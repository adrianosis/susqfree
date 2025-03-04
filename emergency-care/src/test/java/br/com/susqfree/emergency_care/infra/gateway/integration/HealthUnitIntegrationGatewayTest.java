package br.com.susqfree.emergency_care.infra.gateway.integration;

import br.com.susqfree.emergency_care.domain.model.HealthUnit;
import br.com.susqfree.emergency_care.infra.gateway.integration.client.HealthUnitClient;
import br.com.susqfree.emergency_care.infra.gateway.integration.dto.HealthUnitDto;
import br.com.susqfree.emergency_care.infra.gateway.integration.mapper.HealthUnitDtoMapper;
import br.com.susqfree.emergency_care.utils.HealthUnitHelper;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class HealthUnitIntegrationGatewayTest {

    private HealthUnitIntegrationGateway healthUnitIntegrationGateway;

    @Mock
    private HealthUnitClient healthUnitClient;

    private final HealthUnitDtoMapper mapper = new HealthUnitDtoMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        healthUnitIntegrationGateway = new HealthUnitIntegrationGateway(healthUnitClient, mapper);
    }

    @Test
    void shouldFindHealthUnitById() {
        HealthUnitDto dto = HealthUnitHelper.createHealthUnitDto();
        when(healthUnitClient.findById(anyLong())).thenReturn(dto);

        Optional<HealthUnit> optionalHealthUnit = healthUnitIntegrationGateway.findById(1L);

        verify(healthUnitClient, times(1)).findById(anyLong());

        assertThat(optionalHealthUnit).isPresent();
        optionalHealthUnit.ifPresent(healthUnit -> {
            assertThat(healthUnit).isNotNull();
            assertThat(healthUnit.getId()).isEqualTo(dto.getId());
            assertThat(healthUnit.getName()).isEqualTo(dto.getName());
        });
    }

    @Test
    void shouldThrowExceptionWhenHealthUnitNotFound() {
        when(healthUnitClient.findById(anyLong())).thenThrow(FeignException.NotFound.class);

        assertThrows(RuntimeException.class, () -> healthUnitIntegrationGateway.findById(1L));

        verify(healthUnitClient, times(1)).findById(anyLong());
    }
}
