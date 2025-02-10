package br.com.susqfree.doctor_management.domain.usecase;

import br.com.susqfree.doctor_management.domain.gateway.DoctorGateway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeleteDoctorUseCaseTest {

    @InjectMocks
    private DeleteDoctorUseCase deleteDoctorUseCase;

    @Mock
    private DoctorGateway doctorGateway;

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
    void shouldDeleteDoctorSuccessfully() {
        Long doctorId = 1L;

        deleteDoctorUseCase.execute(doctorId);

        verify(doctorGateway).deleteById(doctorId);
        verifyNoMoreInteractions(doctorGateway);
    }

    @Test
    void shouldThrowExceptionWhenDoctorIdIsNull() {
        assertThatThrownBy(() -> deleteDoctorUseCase.execute(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Doctor ID must not be null.");

        verifyNoInteractions(doctorGateway);
    }
}
