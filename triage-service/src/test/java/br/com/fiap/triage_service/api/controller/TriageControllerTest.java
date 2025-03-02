package br.com.fiap.triage_service.api.controller;

import br.com.fiap.triage_service.api.constants.TriageConstants;
import br.com.fiap.triage_service.domain.input.TriageInput;
import br.com.fiap.triage_service.domain.output.TriageOutput;
import br.com.fiap.triage_service.domain.output.TriagePriorityOutput;
import br.com.fiap.triage_service.domain.usecase.CreateTriageUseCase;
import br.com.fiap.triage_service.domain.usecase.FindAllTriageUseCase;
import br.com.fiap.triage_service.domain.usecase.FindTriagesByPatientIdUseCase;
import br.com.fiap.triage_service.helper.TriageHelper;
import br.com.fiap.triage_service.infra.exception.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static br.com.fiap.triage_service.helper.TriageHelper.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TriageControllerTest {

    private static final String BASE_URL = "/triage";

    private MockMvc mockMvc;

    @Mock
    private CreateTriageUseCase createTriageUseCase;
    @Mock
    private FindAllTriageUseCase findAllTriageUseCase;
    @Mock
    private FindTriagesByPatientIdUseCase findTriagesByPatientIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        TriageController healthUnitController = new TriageController(createTriageUseCase,
                findTriagesByPatientIdUseCase,findAllTriageUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(healthUnitController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void shouldCreateTriage() throws Exception {
        TriageInput triageInput = TriageHelper.createTriageInput();
        TriagePriorityOutput triagePriorityOutput = TriageHelper.createTriagePriorityOutput();

        when(createTriageUseCase.execute(any(TriageInput.class))).thenReturn(triagePriorityOutput);

        mockMvc.perform(post("/triage/process-triage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(triageInput)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priority").value(triagePriorityOutput.getPriority()))
                .andExpect(jsonPath("$.diagnosis").value(triagePriorityOutput.getDiagnosis()));

        verify(createTriageUseCase, times(1)).execute(any(TriageInput.class));

    }

    @Test
    void shouldGetTriageQuestions() throws Exception {
        Map<String, Object> response = TriageConstants.TRIAGE_QUESTIONS;

        mockMvc.perform(get("/triage/questions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['1. Identificação do Atendimento']").exists())
                .andExpect(jsonPath("$['2. Queixa Principal']").exists())
                .andExpect(jsonPath("$['3. Sinais Vitais']").exists());

    }

    @Test
    void findTriagesByPatient() throws Exception {

        UUID patientId = UUID.fromString("328bcfaa-83af-47cc-a732-f9e44e0d7600");
        List<TriageOutput> triages = List.of(
                TriageHelper.createTriageOutput(1),
                TriageHelper.createTriageOutput(2)
        );
        when(findTriagesByPatientIdUseCase.execute(patientId)).thenReturn(triages);

        // Act & Assert
        mockMvc.perform(get("/triage/by-patient/"+ patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(findTriagesByPatientIdUseCase, times(1)).execute(patientId);
    }

    @Test
    void shouldReturnNotFoundWhenPatientHasNoTriages() throws Exception {
        UUID patientId = UUID.fromString("328bcfaa-83af-47cc-a732-f9e44e0d7600");

        when(findTriagesByPatientIdUseCase.execute(patientId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/triage/by-patient/ " + patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void shouldReturnPagedTriages() throws Exception {
        List<TriageOutput> triages = List.of(
                TriageHelper.createTriageOutput(1),
                TriageHelper.createTriageOutput(2)
        );

        Pageable pageable = PageRequest.of(0, 10);
        Page<TriageOutput> triagePage = new PageImpl<>(triages, pageable, triages.size());

        when(findAllTriageUseCase.execute(any(Pageable.class),
                any(String.class), any(String.class))).thenReturn(triagePage);

        mockMvc.perform(get("/triage")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2));
    }

    @Test
    void shouldReturnBadRequestWhenCreatingInvalidTriage() throws Exception {
        TriageInput invalidInput = TriageInput.builder().build();

        mockMvc.perform(post("/triage/process-triage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidInput)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(createTriageUseCase, never()).execute(any(TriageInput.class));
    }


    @Test
    void shouldReturnNotFoundWhenFindingNonExistentTriageByPatientId() throws Exception {
        UUID nonExistentId = UUID.fromString("328bcfaa-83af-47cc-a732-f9e44e0d7601");

        when(findTriagesByPatientIdUseCase.execute(nonExistentId))
                .thenThrow(new EntityNotFoundException("Triage not found"));

        mockMvc.perform(get("/triage/by-patient/" + nonExistentId))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(findTriagesByPatientIdUseCase, times(1)).execute(nonExistentId);
    }
}