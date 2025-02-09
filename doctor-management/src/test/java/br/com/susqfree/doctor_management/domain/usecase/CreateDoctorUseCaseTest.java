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

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class CreateDoctorUseCaseTest {

    @InjectMocks
    private CreateDoctorUseCase createDoctorUseCase;

    @Mock
    private DoctorGateway doctorGateway;

    private Doctor doctor;

    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);

        Specialty specialty1 = new Specialty(1L, "Cardiologia", "Especialidade do coração");
        Specialty specialty2 = new Specialty(2L, "Neurologia", "Especialidade do sistema nervoso");

        doctor = new Doctor(null, "Dr. Carlos Silva", "123456-SP", "(11) 91234-5678", "carlos.silva@exemplo.com", List.of(specialty1, specialty2));
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldCreateDoctorSuccessfullyWithSpecialties() {
        Doctor savedDoctor = new Doctor(1L, doctor.name(), doctor.crm(), doctor.phone(), doctor.email(), doctor.specialties());
        when(doctorGateway.save(doctor)).thenReturn(savedDoctor);

        Doctor createdDoctor = createDoctorUseCase.execute(doctor);

        assertThat(createdDoctor.id()).isNotNull();
        assertThat(createdDoctor.name()).isEqualTo(doctor.name());
        assertThat(createdDoctor.crm()).isEqualTo(doctor.crm());
        assertThat(createdDoctor.email()).isEqualTo(doctor.email());

        assertThat(createdDoctor.specialties()).hasSize(2);
        assertThat(createdDoctor.specialties()).extracting(Specialty::name).containsExactlyInAnyOrder("Cardiologia", "Neurologia");

        verify(doctorGateway).save(doctor);
        verifyNoMoreInteractions(doctorGateway);
    }
}
