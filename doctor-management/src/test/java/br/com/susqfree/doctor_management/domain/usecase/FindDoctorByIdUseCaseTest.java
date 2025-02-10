package br.com.susqfree.doctor_management.domain.usecase;

import br.com.susqfree.doctor_management.domain.gateway.DoctorGateway;
import br.com.susqfree.doctor_management.domain.model.Doctor;
import br.com.susqfree.doctor_management.domain.model.Specialty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class FindDoctorByIdUseCaseTest {

    @InjectMocks
    private FindDoctorByIdUseCase findDoctorByIdUseCase;

    @Mock
    private DoctorGateway doctorGateway;

    private AutoCloseable openMocks;

    private Doctor expectedDoctor;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);

        Specialty specialty1 = new Specialty(1L, "Cardiologia", "Especialidade do coração");
        Specialty specialty2 = new Specialty(2L, "Neurologia", "Especialidade do sistema nervoso");

        expectedDoctor = new Doctor(1L, "Dr. Carlos Silva", "123456-SP", "(11) 91234-5678", "carlos.silva@exemplo.com", List.of(specialty1, specialty2));
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldReturnDoctorWhenIdExists() {
        when(doctorGateway.findById(1L)).thenReturn(Optional.of(expectedDoctor));

        Optional<Doctor> result = findDoctorByIdUseCase.execute(1L);

        assertThat(result)
                .isPresent()
                .get()
                .satisfies(doctor -> {
                    assertThat(doctor.id()).isEqualTo(expectedDoctor.id());
                    assertThat(doctor.name()).isEqualTo(expectedDoctor.name());
                    assertThat(doctor.specialties()).hasSize(2);
                    assertThat(doctor.specialties())
                            .extracting(Specialty::name)
                            .containsExactlyInAnyOrder("Cardiologia", "Neurologia");
                });

        verify(doctorGateway).findById(1L);
        verifyNoMoreInteractions(doctorGateway);
    }

    @Test
    void shouldReturnEmptyWhenIdDoesNotExist() {
        when(doctorGateway.findById(99L)).thenReturn(Optional.empty());

        Optional<Doctor> result = findDoctorByIdUseCase.execute(99L);

        assertThat(result).isNotPresent();

        verify(doctorGateway).findById(99L);
        verifyNoMoreInteractions(doctorGateway);
    }

    @Test
    void shouldCallGatewayWithCorrectId() {
        findDoctorByIdUseCase.execute(5L);

        verify(doctorGateway).findById(5L);
        verifyNoMoreInteractions(doctorGateway);
    }
}
