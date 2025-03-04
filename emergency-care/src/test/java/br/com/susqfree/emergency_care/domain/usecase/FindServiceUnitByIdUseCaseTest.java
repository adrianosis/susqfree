package br.com.susqfree.emergency_care.domain.usecase;

import br.com.susqfree.emergency_care.domain.gateway.ServiceUnitGateway;
import br.com.susqfree.emergency_care.domain.model.ServiceUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FindServiceUnitByIdUseCaseTest {

    private FindServiceUnitByIdUseCase findServiceUnitByIdUseCase;

    @Mock
    private ServiceUnitGateway serviceUnitGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        findServiceUnitByIdUseCase = new FindServiceUnitByIdUseCase(serviceUnitGateway);
    }

    @Test
    void shouldReturnServiceUnitWhenItExists() {
        Long serviceUnitId = 1L;
        ServiceUnit serviceUnit = new ServiceUnit(serviceUnitId, "Emergency", 50, 10L);

        when(serviceUnitGateway.findById(serviceUnitId)).thenReturn(Optional.of(serviceUnit));

        Optional<ServiceUnit> result = findServiceUnitByIdUseCase.execute(serviceUnitId);

        assertThat(result).isPresent().contains(serviceUnit);
        verify(serviceUnitGateway, times(1)).findById(serviceUnitId);
    }

    @Test
    void shouldReturnEmptyWhenServiceUnitDoesNotExist() {
        Long serviceUnitId = 1L;

        when(serviceUnitGateway.findById(serviceUnitId)).thenReturn(Optional.empty());

        Optional<ServiceUnit> result = findServiceUnitByIdUseCase.execute(serviceUnitId);

        assertThat(result).isEmpty();
        verify(serviceUnitGateway, times(1)).findById(serviceUnitId);
    }
}
