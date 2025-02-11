package br.com.susqfree.health_unit_management.domain.usecase;

import br.com.susqfree.health_unit_management.domain.gateway.HealthUnitGateway;
import br.com.susqfree.health_unit_management.domain.mapper.HealthUnitMapper;
import br.com.susqfree.health_unit_management.domain.model.HealthUnit;
import br.com.susqfree.health_unit_management.domain.output.HealthUnitOutput;
import br.com.susqfree.health_unit_management.infra.gateway.jpa.repository.HealthUnitRepository;
import br.com.susqfree.health_unit_management.utils.HealthUnitHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateHealthUnitUseCaseTest {

    private UpdateHealthUnitUseCase updateHealthUnitUseCase;

    @Mock
    private HealthUnitGateway healthUnitGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        updateHealthUnitUseCase = new UpdateHealthUnitUseCase(healthUnitGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void execute() {
        // Arrange
        var healthUnitInput = HealthUnitHelper.createHealthUnitInput();
        HealthUnit updatedHealthUnit = HealthUnitMapper.toDomain(healthUnitInput);

        when(healthUnitGateway.update(any(Long.class), any(HealthUnit.class))).thenReturn(updatedHealthUnit);

        // Act
        HealthUnitOutput healthUnitOutput = updateHealthUnitUseCase.execute(1L, healthUnitInput);

        // Assert
        assertThat(healthUnitOutput)
                .isNotNull()
                .extracting(HealthUnitOutput::getName, HealthUnitOutput::getType,
                        HealthUnitOutput::getPhone, HealthUnitOutput::getStreet, HealthUnitOutput::getNumber,
                        HealthUnitOutput::getComplement, HealthUnitOutput::getZipcode, HealthUnitOutput::getCity,
                        HealthUnitOutput::getState, HealthUnitOutput::getLatitude, HealthUnitOutput::getLongitude)
                .containsExactly(healthUnitInput.getName(), healthUnitInput.getType(),
                        healthUnitInput.getPhone(), healthUnitInput.getStreet(), healthUnitInput.getNumber(),
                        healthUnitInput.getComplement(), healthUnitInput.getZipcode(), healthUnitInput.getCity(),
                        healthUnitInput.getState(), healthUnitInput.getLatitude(), healthUnitInput.getLongitude());
        verify(healthUnitGateway, times(1)).update(any(Long.class), any(HealthUnit.class));
    }
}