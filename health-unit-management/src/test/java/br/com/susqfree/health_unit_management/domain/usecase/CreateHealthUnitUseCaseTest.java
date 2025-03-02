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

class CreateHealthUnitUseCaseTest {

    private CreateHealthUnitUseCase createHealthUnitUseCase;

    @Mock
    private HealthUnitGateway healthUnitGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        createHealthUnitUseCase = new CreateHealthUnitUseCase(healthUnitGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldExecuteSuccessfully() {
        //Arrange
        HealthUnit healthUnit = HealthUnitHelper.createHealthUnit(null);
        HealthUnit savedHealthUnit = HealthUnitHelper.createHealthUnit(1L);
        HealthUnitInput healthUnitInput = HealthUnitHelper.createHealthUnitInput();

        when(healthUnitGateway.save(any(HealthUnit.class))).thenReturn(savedHealthUnit);

        // Act
        HealthUnitOutput healthUnitOutput = createHealthUnitUseCase.execute(healthUnitInput);

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
        verify(healthUnitGateway, times(1)).save(any(HealthUnit.class));

    }

}