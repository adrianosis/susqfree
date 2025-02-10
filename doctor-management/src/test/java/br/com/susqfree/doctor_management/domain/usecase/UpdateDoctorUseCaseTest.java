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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UpdateDoctorUseCaseTest {

    @InjectMocks
    private UpdateDoctorUseCase updateDoctorUseCase;

    @Mock
    private DoctorGateway doctorGateway;

    private AutoCloseable openMocks;

    private Doctor doctor;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);

        Specialty specialty = new Specialty(1L, "Cardiologia", "Especialidade do coração");
        doctor = new Doctor(1L, "Dr. Carlos Silva", "123456-SP", "(11) 91234-5678", "carlos.silva@exemplo.com", List.of(specialty));
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldUpdateDoctorSuccessfully() {
        when(doctorGateway.findById(doctor.id())).thenReturn(Optional.of(doctor));
        when(doctorGateway.save(doctor)).thenReturn(doctor);

        Doctor updatedDoctor = updateDoctorUseCase.execute(doctor);

        assertThat(updatedDoctor).isNotNull();
        assertThat(updatedDoctor.id()).isEqualTo(doctor.id());
        assertThat(updatedDoctor.name()).isEqualTo(doctor.name());
        assertThat(updatedDoctor.crm()).isEqualTo(doctor.crm());
        assertThat(updatedDoctor.specialties()).hasSize(1);
        assertThat(updatedDoctor.specialties()).extracting(Specialty::name).containsExactly("Cardiologia");

        verify(doctorGateway).findById(doctor.id());
        verify(doctorGateway).save(doctor);
        verifyNoMoreInteractions(doctorGateway);
    }

    @Test
    void shouldThrowExceptionWhenDoctorIdIsNull() {
        doctor = new Doctor(null, doctor.name(), doctor.crm(), doctor.phone(), doctor.email(), doctor.specialties());

        assertThatThrownBy(() -> updateDoctorUseCase.execute(doctor))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Doctor ID must not be null for update.");

        verifyNoInteractions(doctorGateway);
    }

    @Test
    void shouldThrowExceptionWhenDoctorNotFound() {
        when(doctorGateway.findById(doctor.id())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> updateDoctorUseCase.execute(doctor))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Doctor with ID " + doctor.id() + " not found.");

        verify(doctorGateway).findById(doctor.id());
        verifyNoMoreInteractions(doctorGateway);
    }
}
