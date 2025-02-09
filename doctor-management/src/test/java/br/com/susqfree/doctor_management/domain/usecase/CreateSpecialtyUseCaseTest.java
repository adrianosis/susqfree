package br.com.susqfree.doctor_management.domain.usecase;

import br.com.susqfree.doctor_management.domain.gateway.SpecialtyGateway;
import br.com.susqfree.doctor_management.domain.model.Specialty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class CreateSpecialtyUseCaseTest {

    @InjectMocks
    private CreateSpecialtyUseCase createSpecialtyUseCase;

    @Mock
    private SpecialtyGateway specialtyGateway;

    private Specialty specialty;

    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        specialty = new Specialty(null, "Cardiologia", "Especialidade dedicada ao estudo do coração");
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldCreateSpecialtySuccessfully() {
        Specialty savedSpecialty = new Specialty(1L, specialty.name(), specialty.description());
        when(specialtyGateway.save(specialty)).thenReturn(savedSpecialty);

        Specialty createdSpecialty = createSpecialtyUseCase.execute(specialty);

        assertThat(createdSpecialty.id()).isNotNull();
        assertThat(createdSpecialty.name()).isEqualTo(specialty.name());
        assertThat(createdSpecialty.description()).isEqualTo(specialty.description());

        verify(specialtyGateway).save(specialty);
        verifyNoMoreInteractions(specialtyGateway);
    }
}
