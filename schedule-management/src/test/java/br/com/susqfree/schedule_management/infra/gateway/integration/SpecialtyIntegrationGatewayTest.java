package br.com.susqfree.schedule_management.infra.gateway.integration;

import br.com.susqfree.schedule_management.domain.model.Specialty;
import br.com.susqfree.schedule_management.infra.gateway.integration.client.DoctorClient;
import br.com.susqfree.schedule_management.infra.gateway.integration.mapper.SpecialtyDtoMapper;
import br.com.susqfree.schedule_management.utils.SpecialtyHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class SpecialtyIntegrationGatewayTest {

    private SpecialtyIntegrationGateway specialtyIntegrationGateway;

    @Mock
    private DoctorClient doctorClient;
    AutoCloseable openMocks;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        specialtyIntegrationGateway = new SpecialtyIntegrationGateway(doctorClient, new SpecialtyDtoMapper());
    }

    @Test
    public void shouldFindSpecialtyById() {
        // Arrange
        var specialty = SpecialtyHelper.createSpecialtyDto();
        long specialtyId = specialty.getId();

        when(doctorClient.findSpecialtyById(anyLong())).thenReturn(specialty);

        // Act
        var optionalFoundSpecialty = specialtyIntegrationGateway.findById(specialtyId);

        // Assert
        verify(doctorClient, times(1)).findSpecialtyById(anyLong());

        assertThat(optionalFoundSpecialty.isPresent()).isTrue();
        optionalFoundSpecialty.ifPresent(foundSpecialty -> {
            assertThat(foundSpecialty).isInstanceOf(Specialty.class).isNotNull();
            assertThat(foundSpecialty.getId()).isEqualTo(specialty.getId());
            assertThat(foundSpecialty.getName()).isEqualTo(specialty.getName());
        });
    }
}