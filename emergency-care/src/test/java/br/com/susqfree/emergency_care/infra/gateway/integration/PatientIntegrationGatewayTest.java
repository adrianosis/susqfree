package br.com.susqfree.emergency_care.infra.gateway.integration;

import br.com.susqfree.emergency_care.domain.model.Patient;
import br.com.susqfree.emergency_care.infra.gateway.integration.client.PatientClient;
import br.com.susqfree.emergency_care.infra.gateway.integration.dto.PatientDto;
import br.com.susqfree.emergency_care.infra.gateway.integration.mapper.PatientDtoMapper;
import br.com.susqfree.emergency_care.utils.PatientHelper;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PatientIntegrationGatewayTest {

    private PatientIntegrationGateway patientIntegrationGateway;

    @Mock
    private PatientClient patientClient;

    private final PatientDtoMapper mapper = new PatientDtoMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        patientIntegrationGateway = new PatientIntegrationGateway(patientClient, mapper);
    }

    @Test
    void shouldFindPatientById() {
        PatientDto dto = PatientHelper.createPatientDto();
        UUID patientId = dto.getId();

        when(patientClient.findById(any(UUID.class))).thenReturn(dto);

        Optional<Patient> optionalPatient = patientIntegrationGateway.findById(patientId);

        verify(patientClient, times(1)).findById(any(UUID.class));

        assertThat(optionalPatient).isPresent();
        optionalPatient.ifPresent(patient -> {
            assertThat(patient).isNotNull();
            assertThat(patient.getId()).isEqualTo(dto.getId());
            assertThat(patient.getName()).isEqualTo(dto.getName());
            assertThat(patient.getCpf()).isEqualTo(dto.getCpf());
            assertThat(patient.getSusNumber()).isEqualTo(dto.getSusNumber());
        });
    }

    @Test
    void shouldThrowExceptionWhenPatientNotFound() {
        UUID patientId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        when(patientClient.findById(any(UUID.class))).thenThrow(FeignException.NotFound.class);

        assertThrows(RuntimeException.class, () -> patientIntegrationGateway.findById(patientId));

        verify(patientClient, times(1)).findById(any(UUID.class));
    }
}
