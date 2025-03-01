package br.com.susqfree.schedule_management.infra.gateway.integration;

import br.com.susqfree.schedule_management.domain.model.Patient;
import br.com.susqfree.schedule_management.infra.gateway.integration.client.PatientClient;
import br.com.susqfree.schedule_management.infra.gateway.integration.mapper.PatientDtoMapper;
import br.com.susqfree.schedule_management.utils.PatientHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PatientIntegrationGatewayTest {

    private PatientIntegrationGateway patientIntegrationGateway;

    @Mock
    private PatientClient patientClient;
    AutoCloseable openMocks;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        patientIntegrationGateway = new PatientIntegrationGateway(patientClient, new PatientDtoMapper());
    }

    @Test
    public void shouldFindPatientById() {
        // Arrange
        var patient = PatientHelper.createPatientDto();
        UUID patientId = patient.getId();

        when(patientClient.findById(any(UUID.class))).thenReturn(patient);

        // Act
        var optionalFoundPatient = patientIntegrationGateway.findById(patientId);

        // Assert
        verify(patientClient, times(1)).findById(any(UUID.class));

        assertThat(optionalFoundPatient.isPresent()).isTrue();
        optionalFoundPatient.ifPresent(foundPatient -> {
            assertThat(foundPatient).isInstanceOf(Patient.class).isNotNull();
            assertThat(foundPatient.getId()).isEqualTo(patient.getId());
            assertThat(foundPatient.getName()).isEqualTo(patient.getName());
            assertThat(foundPatient.getCpf()).isEqualTo(patient.getCpf());
            assertThat(foundPatient.getSusNumber()).isEqualTo(patient.getSusNumber());
        });
    }

}