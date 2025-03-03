package br.com.fiap.triage_service.domain.usecase;

import br.com.fiap.triage_service.domain.gateway.TriageGateway;
import br.com.fiap.triage_service.domain.model.Triage;
import br.com.fiap.triage_service.domain.output.TriageOutput;
import br.com.fiap.triage_service.helper.TriageHelper;
import br.com.fiap.triage_service.infra.gateway.jpa.entity.PriorityCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class FindTriagesByPatientIdUseCaseTest {
    private FindTriagesByPatientIdUseCase findTriagesByPatientIdUseCase;

    @Mock
    private TriageGateway triageGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        findTriagesByPatientIdUseCase = new FindTriagesByPatientIdUseCase(triageGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldReturnTriagesWhenPatientExists() {
        UUID patientId = UUID.fromString("328bcfaa-83af-47cc-a732-f9e44e0d7600");

        List<Triage> triages = List.of(
                TriageHelper.createTriage(1, PriorityCode.R),
                TriageHelper.createTriage(2, PriorityCode.R)
                );
        when(triageGateway.findByPatientId(any())).thenReturn(triages);

        List<TriageOutput> result = findTriagesByPatientIdUseCase.execute(patientId);

        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .extracting(TriageOutput::getId)
                .containsExactly(1, 2);

        verify(triageGateway, times(1)).findByPatientId(patientId);
    }

    @Test
    void shouldReturnEmptyListWhenNoTriagesFound() {
        UUID patientId = UUID.fromString("328bcfaa-83af-47cc-a732-f9e44e0d7601");

        when(triageGateway.findByPatientId(any())).thenReturn(List.of());

        List<TriageOutput> result = findTriagesByPatientIdUseCase.execute(patientId);

        assertThat(result).isEmpty();
        verify(triageGateway, times(1)).findByPatientId(patientId);
    }}