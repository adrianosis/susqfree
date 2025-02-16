package br.com.susqfree.patient_management.domain.usecase;

import br.com.susqfree.patient_management.domain.gateway.PatientGateway;
import br.com.susqfree.patient_management.domain.mapper.PatientOutputMapper;
import br.com.susqfree.patient_management.domain.model.Patient;
import br.com.susqfree.patient_management.domain.output.PatientOutput;
import br.com.susqfree.patient_management.utils.PatientHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FindPatientByIdUseCaseTest {

    private FindPatientByIdUseCase findPatientByIdUseCase;

    @Mock
    private PatientGateway patientGateway;
    AutoCloseable openMocks;


    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        findPatientByIdUseCase = new FindPatientByIdUseCase(patientGateway, new PatientOutputMapper());
    }

    @Test
    public void shouldCreatePatient() {
        // Arrange
        UUID patientId = UUID.randomUUID();
        Patient patient = PatientHelper.createPatient(patientId);

        when(patientGateway.findById(any(UUID.class))).thenReturn(Optional.ofNullable(patient));

        // Act
        var foundPatient = findPatientByIdUseCase.execute(patientId);

        // Assert
        verify(patientGateway, times(1)).findById(any(UUID.class));

        assertThat(foundPatient).isInstanceOf(PatientOutput.class).isNotNull();
        assertThat(foundPatient.getName()).isEqualTo(patient.getName());
        assertThat(foundPatient.getGender()).isEqualTo(patient.getGender());
        assertThat(foundPatient.getCpf()).isEqualTo(patient.getCpf());
        assertThat(foundPatient.getSusNumber()).isEqualTo(patient.getSusNumber());
        assertThat(foundPatient.getPhoneNumber()).isEqualTo(patient.getPhoneNumber());
        assertThat(foundPatient.getMail()).isEqualTo(patient.getMail());
        assertThat(foundPatient.getStreet()).isEqualTo(patient.getAddress().getStreet());
        assertThat(foundPatient.getNumber()).isEqualTo(patient.getAddress().getNumber());
        assertThat(foundPatient.getDistrict()).isEqualTo(patient.getAddress().getDistrict());
        assertThat(foundPatient.getCity()).isEqualTo(patient.getAddress().getCity());
        assertThat(foundPatient.getState()).isEqualTo(patient.getAddress().getState());
        assertThat(foundPatient.getPostalCode()).isEqualTo(patient.getAddress().getPostalCode());
        assertThat(foundPatient.getLatitude()).isEqualTo(patient.getAddress().getLatitude());
        assertThat(foundPatient.getLongitude()).isEqualTo(patient.getAddress().getLongitude());
    }

}