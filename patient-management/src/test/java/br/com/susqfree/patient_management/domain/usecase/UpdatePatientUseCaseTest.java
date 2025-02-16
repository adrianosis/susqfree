package br.com.susqfree.patient_management.domain.usecase;

import br.com.susqfree.patient_management.domain.gateway.PatientGateway;
import br.com.susqfree.patient_management.domain.input.UpdatePatientInput;
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

public class UpdatePatientUseCaseTest {

    private UpdatePatientUseCase updatePatientUseCase;

    @Mock
    private PatientGateway patientGateway;
    AutoCloseable openMocks;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        updatePatientUseCase = new UpdatePatientUseCase(patientGateway, new PatientOutputMapper());
    }

    @Test
    public void shouldUpdatePatient() {
        // Arrange
        UUID patientId = UUID.randomUUID();
        Patient patient = PatientHelper.createPatient(patientId);

        when(patientGateway.findById(any(UUID.class))).thenReturn(Optional.ofNullable(patient));
        when(patientGateway.save(any(Patient.class))).thenReturn(patient);

        var input = UpdatePatientInput.builder()
                .name("Julia Silva")
                .gender("O")
                .phoneNumber("11999993333")
                .mail("julia.silva@test.com")
                .street("Rua Capitão")
                .number("1450")
                .district("Limões")
                .city("Santa Catarina")
                .state("SC")
                .postalCode("22224444")
                .latitude(-23.562643)
                .longitude(-46.654888)
                .build();

        // Act
        var savedPatient = updatePatientUseCase.execute(patientId, input);

        // Assert
        verify(patientGateway, times(1)).findById(any(UUID.class));
        verify(patientGateway, times(1)).save(any(Patient.class));

        assertThat(savedPatient).isInstanceOf(PatientOutput.class).isNotNull();
        assertThat(savedPatient.getName()).isEqualTo(input.getName());
        assertThat(savedPatient.getGender()).isEqualTo(input.getGender());
        assertThat(savedPatient.getPhoneNumber()).isEqualTo(input.getPhoneNumber());
        assertThat(savedPatient.getMail()).isEqualTo(input.getMail());
        assertThat(savedPatient.getStreet()).isEqualTo(input.getStreet());
        assertThat(savedPatient.getNumber()).isEqualTo(input.getNumber());
        assertThat(savedPatient.getDistrict()).isEqualTo(input.getDistrict());
        assertThat(savedPatient.getCity()).isEqualTo(input.getCity());
        assertThat(savedPatient.getState()).isEqualTo(input.getState());
        assertThat(savedPatient.getPostalCode()).isEqualTo(input.getPostalCode());
        assertThat(savedPatient.getLatitude()).isEqualTo(input.getLatitude());
        assertThat(savedPatient.getLongitude()).isEqualTo(input.getLongitude());
    }

}