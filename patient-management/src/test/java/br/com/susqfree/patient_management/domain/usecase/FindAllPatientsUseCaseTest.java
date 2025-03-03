package br.com.susqfree.patient_management.domain.usecase;

import br.com.susqfree.patient_management.domain.gateway.PatientGateway;
import br.com.susqfree.patient_management.domain.mapper.PatientOutputMapper;
import br.com.susqfree.patient_management.domain.model.Patient;
import br.com.susqfree.patient_management.utils.PatientHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FindAllPatientsUseCaseTest {

    private FindAllPatientsUseCase findAllPatientsUseCase;

    @Mock
    private PatientGateway patientGateway;
    AutoCloseable openMocks;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        findAllPatientsUseCase = new FindAllPatientsUseCase(patientGateway, new PatientOutputMapper());
    }

    @Test
    public void shouldFindAllPatients() {
        // Arrange
        Patient patient1 = PatientHelper.createPatient(UUID.randomUUID());
        Patient patient2 = PatientHelper.createPatient(UUID.randomUUID());

        when(patientGateway.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(patient1, patient2)));

        // Act
        var foundPatients = findAllPatientsUseCase.execute(PageRequest.of(0, 10));

        // Assert
        verify(patientGateway, times(1)).findAll(any(Pageable.class));

        assertThat(foundPatients).hasSize(2);
    }

}