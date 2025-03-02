package br.com.fiap.triage_service.domain.usecase;

import br.com.fiap.triage_service.domain.gateway.PatientGateway;
import br.com.fiap.triage_service.domain.gateway.TriageGateway;
import br.com.fiap.triage_service.domain.input.TriageInput;
import br.com.fiap.triage_service.domain.mapper.TriageMapper;
import br.com.fiap.triage_service.domain.model.Triage;
import br.com.fiap.triage_service.domain.output.TriagePriorityOutput;
import br.com.fiap.triage_service.helper.TriageHelper;
import br.com.fiap.triage_service.infra.gateway.jpa.entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateTriageUseCaseTest {

    private CreateTriageUseCase createTriageUseCase;

    @Mock
    private TriageGateway triageGateway;

    @Mock
    private PatientGateway patientGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        createTriageUseCase = new CreateTriageUseCase(triageGateway, patientGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldCreateEmergencyTriage() {
        TriageInput triageInput= TriageHelper.createTriageInput();
        Triage triage = TriageMapper.toDomain(triageInput);
        Triage savedTriage = TriageHelper.createTriage(1, PriorityCode.R);

        when(triageGateway.create(any(Triage.class))).thenReturn(savedTriage);
        when(patientGateway.existsPatient(any())).thenReturn(true);

        TriagePriorityOutput output = createTriageUseCase.execute(triageInput);

        assertThat(output)
                .isNotNull()
                .extracting(TriagePriorityOutput::getPriority, TriagePriorityOutput::getDiagnosis)
                .containsExactly(savedTriage.getPriority().toString(), "Emergência - Atendimento imediato necessário. Condição com risco de vida.");
        verify(triageGateway, times(1)).create(any(Triage.class));

    }

    @Test
    void shouldCreateUrgentTriage() {
        TriageInput triageInput= TriageHelper.createUrgentTriageInput();
        Triage triage = TriageMapper.toDomain(triageInput);
        Triage savedTriage = TriageHelper.createUrgentTriage(1, PriorityCode.Y);

        when(triageGateway.create(any(Triage.class))).thenReturn(savedTriage);
        when(patientGateway.existsPatient(any())).thenReturn(true);

        TriagePriorityOutput output = createTriageUseCase.execute(triageInput);

        assertThat(output)
                .isNotNull()
                .extracting(TriagePriorityOutput::getPriority, TriagePriorityOutput::getDiagnosis)
                .containsExactly(savedTriage.getPriority().toString(), "Urgência - Necessita de atenção urgente. Condição grave.");
        verify(triageGateway, times(1)).create(any(Triage.class));
    }

    @Test
    void shouldCreateWithoutPriorityTriage() {
        TriageInput triageInput= TriageHelper.createTriageWithoutPriorityInput();
        Triage triage = TriageMapper.toDomain(triageInput);
        Triage savedTriage = TriageHelper.createTriage(1, PriorityCode.B);

        when(triageGateway.create(any(Triage.class))).thenReturn(savedTriage);
        when(patientGateway.existsPatient(any())).thenReturn(true);

        TriagePriorityOutput output = createTriageUseCase.execute(triageInput);

        assertThat(output)
                .isNotNull()
                .extracting(TriagePriorityOutput::getPriority, TriagePriorityOutput::getDiagnosis)
                .containsExactly(savedTriage.getPriority().toString(), "Não prioritário - Pode aguardar atendimento.");
        verify(triageGateway, times(1)).create(any(Triage.class));

    }

    @Test
    void shouldCreateWithPriorityTriage() {
        TriageInput triageInput= TriageHelper.createTriageWithPriorityInput();
        Triage triage = TriageMapper.toDomain(triageInput);
        Triage savedTriage = TriageHelper.createTriage(1, PriorityCode.G);

        when(triageGateway.create(any(Triage.class))).thenReturn(savedTriage);
        when(patientGateway.existsPatient(any())).thenReturn(true);

        TriagePriorityOutput output = createTriageUseCase.execute(triageInput);

        assertThat(output)
                .isNotNull()
                .extracting(TriagePriorityOutput::getPriority, TriagePriorityOutput::getDiagnosis)
                .containsExactly(savedTriage.getPriority().toString(), "Prioritário - Requer atenção médica, mas não é grave.");
        verify(triageGateway, times(1)).create(any(Triage.class));

    }


    @Test
    void shouldCreateTriageWithoutSomeFields() {
        TriageInput triageInput =TriageInput.builder()
                .patientId(UUID.fromString("328bcfaa-83af-47cc-a732-f9e44e0d7600"))
                .consultationReason("Sono em excesso")
                .symptomDuration(SymptomDuration.QUATRO_A_SETE_DIAS)
                .bodyTemperature(35)
                .commonSymptoms(Arrays.asList())
                .chronicDiseases(Arrays.asList())
                .continuousMedications(Arrays.asList())
                .allergies(Arrays.asList())
                .build();
        Triage triage = TriageMapper.toDomain(triageInput);
        Triage savedTriage = TriageHelper.createTriage(1, PriorityCode.B);

        when(triageGateway.create(any(Triage.class))).thenReturn(savedTriage);
        when(patientGateway.existsPatient(any())).thenReturn(true);

        TriagePriorityOutput output = createTriageUseCase.execute(triageInput);

        assertThat(output)
                .isNotNull()
                .extracting(TriagePriorityOutput::getPriority, TriagePriorityOutput::getDiagnosis)
                .containsExactly(savedTriage.getPriority().toString(), "Não prioritário - Pode aguardar atendimento.");
        verify(triageGateway, times(1)).create(any(Triage.class));
    }

}