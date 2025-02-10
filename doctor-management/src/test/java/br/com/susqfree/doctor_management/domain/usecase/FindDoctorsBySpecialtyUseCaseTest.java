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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FindDoctorsBySpecialtyUseCaseTest {

    @InjectMocks
    private FindDoctorsBySpecialtyUseCase findDoctorsBySpecialtyUseCase;

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
    void shouldReturnDoctorsWhenSpecialtyExists() {
        String specialtyName = "Cardiologia";

        Specialty specialty = new Specialty(1L, specialtyName, "Especialidade do coração");
        Doctor doctor1 = new Doctor(1L, "Dr. Carlos Silva", "123456-SP", "(11) 91234-5678", "carlos.silva@exemplo.com", List.of(specialty));
        Doctor doctor2 = new Doctor(2L, "Dr. Ana Souza", "654321-SP", "(21) 99876-5432", "ana.souza@exemplo.com", List.of(specialty));

        when(doctorGateway.findBySpecialtyName(specialtyName)).thenReturn(List.of(doctor1, doctor2));

        List<Doctor> doctors = findDoctorsBySpecialtyUseCase.execute(specialtyName);

        assertThat(doctors).hasSize(2);
        assertThat(doctors)
                .extracting(Doctor::name)
                .containsExactlyInAnyOrder("Dr. Carlos Silva", "Dr. Ana Souza");

        assertThat(doctors.get(0).specialties()).extracting(Specialty::name).contains(specialtyName);

        verify(doctorGateway).findBySpecialtyName(specialtyName);
        verifyNoMoreInteractions(doctorGateway);
    }

    @Test
    void shouldReturnEmptyListWhenNoDoctorsFound() {
        String specialtyName = "Inexistente";

        when(doctorGateway.findBySpecialtyName(specialtyName)).thenReturn(List.of());

        List<Doctor> doctors = findDoctorsBySpecialtyUseCase.execute(specialtyName);

        assertThat(doctors).isEmpty();

        verify(doctorGateway).findBySpecialtyName(specialtyName);
        verifyNoMoreInteractions(doctorGateway);
    }
}
