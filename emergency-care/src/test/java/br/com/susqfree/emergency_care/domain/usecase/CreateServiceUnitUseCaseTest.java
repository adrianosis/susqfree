package br.com.susqfree.emergency_care.domain.usecase;

import br.com.susqfree.emergency_care.config.exception.HealthUnitNotFoundException;
import br.com.susqfree.emergency_care.domain.gateway.HealthUnitGateway;
import br.com.susqfree.emergency_care.domain.gateway.ServiceUnitGateway;
import br.com.susqfree.emergency_care.domain.model.HealthUnit;
import br.com.susqfree.emergency_care.domain.model.ServiceUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateServiceUnitUseCaseTest {

    private CreateServiceUnitUseCase createServiceUnitUseCase;

    @Mock
    private ServiceUnitGateway serviceUnitGateway;

    @Mock
    private HealthUnitGateway healthUnitGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        createServiceUnitUseCase = new CreateServiceUnitUseCase(serviceUnitGateway, healthUnitGateway);
    }

    @Test
    void shouldCreateServiceUnitWhenHealthUnitExists() {
        Long unitId = 1L;
        ServiceUnit serviceUnit = new ServiceUnit(2L, "Emergency", 50, unitId);
        HealthUnit healthUnit = new HealthUnit(unitId, "Hospital Municipal São Paulo");

        when(healthUnitGateway.findById(unitId)).thenReturn(Optional.of(healthUnit));
        when(serviceUnitGateway.save(any(ServiceUnit.class))).thenAnswer(invocation -> invocation.getArgument(0));

        createServiceUnitUseCase.execute(serviceUnit);

        verify(healthUnitGateway, times(1)).findById(unitId);
        verify(serviceUnitGateway, times(1)).save(serviceUnit);
    }

    @Test
    void shouldThrowExceptionWhenHealthUnitNotFound() {
        Long unitId = 1L;
        ServiceUnit serviceUnit = new ServiceUnit(2L, "Emergency", 50, unitId);

        when(healthUnitGateway.findById(unitId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> createServiceUnitUseCase.execute(serviceUnit))
                .isInstanceOf(HealthUnitNotFoundException.class)
                .hasMessage("Service unit not found");

        verify(healthUnitGateway, times(1)).findById(unitId);
        verify(serviceUnitGateway, never()).save(any(ServiceUnit.class));
    }
}
