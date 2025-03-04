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

class ListAllServiceUnitsUseCaseTest {

    private ListAllServiceUnitsUseCase listAllServiceUnitsUseCase;

    @Mock
    private ServiceUnitGateway serviceUnitGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        listAllServiceUnitsUseCase = new ListAllServiceUnitsUseCase(serviceUnitGateway);
    }

    @Test
    void shouldReturnAllServiceUnits() {
        List<ServiceUnit> serviceUnits = List.of(
                new ServiceUnit(1L, "Emergency", 50, 10L),
                new ServiceUnit(2L, "Urgency", 30, 20L)
        );

        when(serviceUnitGateway.findAll()).thenReturn(serviceUnits);

        List<ServiceUnit> result = listAllServiceUnitsUseCase.execute();

        assertThat(result).hasSize(2).containsExactlyElementsOf(serviceUnits);
        verify(serviceUnitGateway, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoServiceUnitsExist() {
        when(serviceUnitGateway.findAll()).thenReturn(List.of());

        List<ServiceUnit> result = listAllServiceUnitsUseCase.execute();

        assertThat(result).isEmpty();
        verify(serviceUnitGateway, times(1)).findAll();
    }
}
