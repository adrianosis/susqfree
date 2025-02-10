package br.com.susqfree.doctor_management.domain.usecase;

import br.com.susqfree.doctor_management.domain.gateway.SpecialtyGateway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeleteSpecialtyUseCaseTest {

    @InjectMocks
    private DeleteSpecialtyUseCase deleteSpecialtyUseCase;

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
    void shouldDeleteSpecialtySuccessfully() {
        Long specialtyId = 1L;

        deleteSpecialtyUseCase.execute(specialtyId);

        verify(specialtyGateway).deleteById(specialtyId);
        verifyNoMoreInteractions(specialtyGateway);
    }

    @Test
    void shouldThrowExceptionWhenSpecialtyIdIsNull() {
        assertThatThrownBy(() -> deleteSpecialtyUseCase.execute(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Specialty ID must not be null.");

        verifyNoInteractions(specialtyGateway);
    }
}
