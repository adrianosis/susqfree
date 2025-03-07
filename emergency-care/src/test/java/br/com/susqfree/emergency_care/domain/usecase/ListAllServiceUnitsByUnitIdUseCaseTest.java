package br.com.susqfree.emergency_care.domain.usecase;

import br.com.susqfree.emergency_care.domain.gateway.ServiceUnitGateway;
import br.com.susqfree.emergency_care.domain.model.ServiceUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ListAllServiceUnitsByUnitIdUseCaseTest {

    private ListAllServiceUnitsByUnitIdUseCase listAllServiceUnitsByUnitIdUseCase;

    @Mock
    private ServiceUnitGateway serviceUnitGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        listAllServiceUnitsByUnitIdUseCase = new ListAllServiceUnitsByUnitIdUseCase(serviceUnitGateway);
    }

    @Test
    void shouldReturnAllServiceUnitsByUnitId() {
        List<ServiceUnit> serviceUnits = List.of(
                new ServiceUnit(1L, "Emergency", 50, 10L),
                new ServiceUnit(2L, "Urgency", 30, 20L)
        );

        when(serviceUnitGateway.findAllByUnitId(anyLong())).thenReturn(serviceUnits);

        List<ServiceUnit> result = listAllServiceUnitsByUnitIdUseCase.execute(anyLong());

        assertThat(result).hasSize(2).containsExactlyElementsOf(serviceUnits);
        verify(serviceUnitGateway, times(1)).findAllByUnitId(anyLong());
    }

    @Test
    void shouldReturnEmptyListWhenNoServiceUnitsExist() {
        when(serviceUnitGateway.findAllByUnitId(anyLong())).thenReturn(List.of());

        List<ServiceUnit> result = listAllServiceUnitsByUnitIdUseCase.execute(anyLong());

        assertThat(result).isEmpty();
        verify(serviceUnitGateway, times(1)).findAllByUnitId(anyLong());
    }
}
