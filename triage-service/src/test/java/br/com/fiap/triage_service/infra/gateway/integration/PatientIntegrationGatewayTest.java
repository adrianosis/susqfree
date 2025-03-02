package br.com.fiap.triage_service.infra.gateway.integration;

import br.com.fiap.triage_service.helper.PatientHelper;
import br.com.fiap.triage_service.infra.gateway.integration.client.PatientClient;
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
        patientIntegrationGateway = new PatientIntegrationGateway(patientClient);
    }

    @Test
    public void shouldFindPatientById() {
        var patient = PatientHelper.createPatientDto();
        UUID patientId = patient.getId();

        when(patientClient.findById(any(UUID.class))).thenReturn(patient);

        Boolean result = patientIntegrationGateway.existsPatient(patientId);

        verify(patientClient, times(1)).findById(any(UUID.class));

        assertThat(result).isTrue();
        verify(patientClient, times(1)).findById(any(UUID.class));
    }
}