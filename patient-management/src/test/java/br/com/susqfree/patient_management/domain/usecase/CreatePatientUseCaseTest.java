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

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreatePatientUseCaseTest {

    private CreatePatientUseCase createPatientUseCase;

    @Mock
    private PatientGateway patientGateway;
    AutoCloseable openMocks;


    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        createPatientUseCase = new CreatePatientUseCase(patientGateway, new PatientOutputMapper());
    }

    @Test
    public void shouldCreatePatient() {
        // Arrange
        when(patientGateway.save(any(Patient.class))).thenAnswer(returnsFirstArg());

        var input = PatientHelper.createPatientInput();

        // Act
        var savedPatient = createPatientUseCase.execute(input, UUID.randomUUID());

        // Assert
        verify(patientGateway, times(1)).save(any(Patient.class));

        assertThat(savedPatient).isInstanceOf(PatientOutput.class).isNotNull();
        assertThat(savedPatient.getName()).isEqualTo(input.getName());
        assertThat(savedPatient.getGender()).isEqualTo(input.getGender());
        assertThat(savedPatient.getBirthDate()).isEqualTo(input.getBirthDate());
        assertThat(savedPatient.getCpf()).isEqualTo(input.getCpf());
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