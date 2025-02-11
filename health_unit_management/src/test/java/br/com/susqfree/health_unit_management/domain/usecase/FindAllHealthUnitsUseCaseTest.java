package br.com.susqfree.health_unit_management.domain.usecase;

import br.com.susqfree.health_unit_management.domain.gateway.HealthUnitGateway;
import br.com.susqfree.health_unit_management.domain.output.HealthUnitOutput;
import br.com.susqfree.health_unit_management.utils.HealthUnitHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FindAllHealthUnitsUseCaseTest {
    private FindAllHealthUnitsUseCase findAllHealthUnitsUseCase;

    @Mock
    private HealthUnitGateway healthUnitGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        findAllHealthUnitsUseCase = new FindAllHealthUnitsUseCase(healthUnitGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
    @Test
    void execute() {
        // Arrange
        var healthUnit1 = HealthUnitHelper.createHealthUnit(1l);
        var healthUnit2 = HealthUnitHelper.createHealthUnit(2l);
        when(healthUnitGateway.findAll()).thenReturn(Arrays.asList(healthUnit1, healthUnit2));

        // Act
        var healthUnits = findAllHealthUnitsUseCase.execute();

        // Assert
        Assertions.assertThat(healthUnits)
                .hasSize(2)
                .extracting(HealthUnitOutput::getId)
                .containsExactly(healthUnit1.getId(), healthUnit2.getId());
    }
}