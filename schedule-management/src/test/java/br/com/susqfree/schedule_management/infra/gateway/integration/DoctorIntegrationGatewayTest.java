package br.com.susqfree.schedule_management.infra.gateway.integration;

import br.com.susqfree.schedule_management.domain.model.Doctor;
import br.com.susqfree.schedule_management.infra.gateway.integration.client.DoctorClient;
import br.com.susqfree.schedule_management.infra.gateway.integration.mapper.DoctorDtoMapper;
import br.com.susqfree.schedule_management.utils.DoctorHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DoctorIntegrationGatewayTest {

    private DoctorIntegrationGateway doctorIntegrationGateway;

    @Mock
    private DoctorClient doctorClient;
    AutoCloseable openMocks;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        doctorIntegrationGateway = new DoctorIntegrationGateway(doctorClient, new DoctorDtoMapper());
    }

    @Test
    public void shouldFindDoctorById() {
        // Arrange
        var doctor = DoctorHelper.createDoctorDto();
        long doctorId = doctor.getId();

        when(doctorClient.findDoctorById(anyLong())).thenReturn(doctor);

        // Act
        var optionalFoundDoctor = doctorIntegrationGateway.findById(doctorId);

        // Assert
        verify(doctorClient, times(1)).findDoctorById(anyLong());

        assertThat(optionalFoundDoctor.isPresent()).isTrue();
        optionalFoundDoctor.ifPresent(foundDoctor -> {
            assertThat(foundDoctor).isInstanceOf(Doctor.class).isNotNull();
            assertThat(foundDoctor.getId()).isEqualTo(doctor.getId());
            assertThat(foundDoctor.getName()).isEqualTo(doctor.getName());
            assertThat(foundDoctor.getCrm()).isEqualTo(doctor.getCrm());
        });
    }

}