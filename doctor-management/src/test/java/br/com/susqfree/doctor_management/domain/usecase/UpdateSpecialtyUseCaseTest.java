package br.com.susqfree.doctor_management.domain.usecase;

import br.com.susqfree.doctor_management.domain.gateway.SpecialtyGateway;
import br.com.susqfree.doctor_management.domain.model.Specialty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UpdateSpecialtyUseCaseTest {

    @InjectMocks
    private UpdateSpecialtyUseCase updateSpecialtyUseCase;

    @Mock
    private SpecialtyGateway specialtyGateway;

    private AutoCloseable openMocks;

    private Specialty specialty;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        specialty = new Specialty(1L, "Cardiologia", "Especialidade do coração");
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldUpdateSpecialtySuccessfully() {
        when(specialtyGateway.findById(specialty.id())).thenReturn(Optional.of(specialty));
        when(specialtyGateway.save(specialty)).thenReturn(specialty);

        Specialty updatedSpecialty = updateSpecialtyUseCase.execute(specialty);

        assertThat(updatedSpecialty).isNotNull();
        assertThat(updatedSpecialty.id()).isEqualTo(specialty.id());
        assertThat(updatedSpecialty.name()).isEqualTo(specialty.name());
        assertThat(updatedSpecialty.description()).isEqualTo(specialty.description());

        verify(specialtyGateway).findById(specialty.id());
        verify(specialtyGateway).save(specialty);
        verifyNoMoreInteractions(specialtyGateway);
    }

    @Test
    void shouldThrowExceptionWhenSpecialtyIdIsNull() {
        specialty = new Specialty(null, specialty.name(), specialty.description());

        assertThatThrownBy(() -> updateSpecialtyUseCase.execute(specialty))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Specialty ID must not be null for update.");

        verifyNoInteractions(specialtyGateway);
    }

    @Test
    void shouldThrowExceptionWhenSpecialtyNotFound() {
        when(specialtyGateway.findById(specialty.id())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> updateSpecialtyUseCase.execute(specialty))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Specialty with ID " + specialty.id() + " not found.");

        verify(specialtyGateway).findById(specialty.id());
        verifyNoMoreInteractions(specialtyGateway);
    }
}
