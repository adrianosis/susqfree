package br.com.susqfree.doctor_management.domain.usecase;

import br.com.susqfree.doctor_management.domain.gateway.SpecialtyGateway;
import br.com.susqfree.doctor_management.domain.model.Specialty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class FindAllSpecialtiesUseCaseTest {

    @InjectMocks
    private FindAllSpecialtiesUseCase findAllSpecialtiesUseCase;

    @Mock
    private SpecialtyGateway specialtyGateway;

    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldReturnListOfSpecialties() {
        Specialty specialty1 = new Specialty(1L, "Cardiologia", "Estudo do coração");
        Specialty specialty2 = new Specialty(2L, "Neurologia", "Estudo do sistema nervoso");

        when(specialtyGateway.findAll()).thenReturn(List.of(specialty1, specialty2));

        List<Specialty> specialties = findAllSpecialtiesUseCase.execute();

        assertThat(specialties)
                .isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrder(specialty1, specialty2);

        verify(specialtyGateway).findAll();
        verifyNoMoreInteractions(specialtyGateway);
    }

    @Test
    void shouldReturnEmptyListWhenNoSpecialtiesFound() {
        when(specialtyGateway.findAll()).thenReturn(Collections.emptyList());

        List<Specialty> specialties = findAllSpecialtiesUseCase.execute();

        assertThat(specialties).isEmpty();

        verify(specialtyGateway).findAll();
        verifyNoMoreInteractions(specialtyGateway);
    }
}
