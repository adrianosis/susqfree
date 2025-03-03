package br.com.susqfree.health_unit_management.domain.usecase;

import br.com.susqfree.health_unit_management.domain.gateway.HealthUnitGateway;
import br.com.susqfree.health_unit_management.domain.input.HealthUnitInput;
import br.com.susqfree.health_unit_management.domain.model.HealthUnit;
import br.com.susqfree.health_unit_management.domain.output.HealthUnitOutput;
import br.com.susqfree.health_unit_management.utils.HealthUnitHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FindHealthUnitByIdUseCaseTest {
    private FindHealthUnitByIdUseCase findHealthUnitByIdUseCase;

    @Mock
    private HealthUnitGateway healthUnitGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        findHealthUnitByIdUseCase = new FindHealthUnitByIdUseCase(healthUnitGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void execute() {
        // Arrange
        Long id = 1L;
        HealthUnit healthUnit = HealthUnitHelper.createHealthUnit(null);
        HealthUnit savedHealthUnit = HealthUnitHelper.createHealthUnit(id);
        HealthUnitInput healthUnitInput = HealthUnitHelper.createHealthUnitInput();

        when(healthUnitGateway.findById(any(Long.class))).thenReturn(savedHealthUnit);

        // Act
        HealthUnitOutput healthUnitOutput = findHealthUnitByIdUseCase.execute(id);

        // Assert
        assertThat(healthUnitOutput)
                .isNotNull()
                .extracting(HealthUnitOutput::getId, HealthUnitOutput::getName, HealthUnitOutput::getType,
                        HealthUnitOutput::getPhone, HealthUnitOutput::getStreet, HealthUnitOutput::getNumber,
                        HealthUnitOutput::getComplement, HealthUnitOutput::getZipcode, HealthUnitOutput::getCity,
                        HealthUnitOutput::getState, HealthUnitOutput::getLatitude, HealthUnitOutput::getLongitude)
                .containsExactly(1L, healthUnitInput.getName(), healthUnitInput.getType(),
                        healthUnitInput.getPhone(), healthUnitInput.getStreet(), healthUnitInput.getNumber(),
                        healthUnitInput.getComplement(), healthUnitInput.getZipcode(), healthUnitInput.getCity(),
                        healthUnitInput.getState(), healthUnitInput.getLatitude(), healthUnitInput.getLongitude());
        verify(healthUnitGateway, times(1)).findById(any(Long.class));
    }
}