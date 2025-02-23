package br.com.fiap.triage_service.api.controller;

import br.com.fiap.triage_service.api.constants.TriageConstants;
import br.com.fiap.triage_service.domain.input.TriageInput;
import br.com.fiap.triage_service.domain.output.TriageOutput;
import br.com.fiap.triage_service.domain.output.TriagePriorityOutput;
import br.com.fiap.triage_service.domain.usecase.CreateTriageUseCase;
import br.com.fiap.triage_service.domain.usecase.FindAllTriageUseCase;
import br.com.fiap.triage_service.domain.usecase.FindTriagesByPatientIdUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("triage")
@Tag(name = "Triage ", description = "Operations related to health triage")
public class TriageController {

    private final CreateTriageUseCase createTriageUseCase;
    private final FindTriagesByPatientIdUseCase findTriagesByPatientIdUseCase;
    private final FindAllTriageUseCase findAllTriageUseCase;

    @PostMapping("/process-triage")
    @Operation(
            summary = "Process a triage and return the patient's priority",
            description = "This endpoint receives the patient's data and determines the triage priority.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Triage successfully processed",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    name = "Emergência",
                                                    value = """
                            {
                                "priorityCode": "R",
                                "diagnosis": "Emergência - Atendimento imediato necessário. Condição com risco de vida."
                            }
                            """
                                            ),
                                            @ExampleObject(
                                                    name = "Urgência",
                                                    value = """
                            {
                                "priorityCode": "Y",
                                "diagnosis": "Urgência - Necessita de atenção urgente. Condição grave."
                            }
                            """
                                            ),
                                            @ExampleObject(
                                                    name = "Prioritário",
                                                    value = """
                            {
                                "priorityCode": "G",
                                "diagnosis": "Prioritário - Requer atenção médica, mas não é grave."
                            }
                            """
                                            ),
                                            @ExampleObject(
                                                    name = "Não prioritário",
                                                    value = """
                            {
                                "priorityCode": "B",
                                "diagnosis": "Não prioritário - Pode aguardar atendimento."
                            }
                            """
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    name = "Validation error",
                                                    value = """
                            {
                                "message": "O campo 'patientId' é obrigatório."
                            }
                            """
                                            )
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<TriagePriorityOutput> create(@RequestBody @Valid TriageInput input) {
        TriagePriorityOutput output = createTriageUseCase.execute(input);
        return ResponseEntity.ok(output);
    }


    @Operation(summary = "Get triage questions", description = "This endpoint returns a map containing the triage questions and possible answers.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success - Returns the triage questions",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                {
                    "Identificação do Atendimento": "ID gerado pelo sistema",
                    "Queixa Principal": {
                        "Motivo da Consulta": "Campo livre",
                        "Duração dos Sintomas": ["Menos de 24 horas", "1 a 3 dias", "4 a 7 dias", "Mais de uma semana"]
                    },
                    "Sinais Vitais": {
                        "Temperatura Corporal (°C)": "Valor numérico",
                        "Pressão Arterial": ["Normal", "Elevada", "Hipertensão", "Hipotensão"],
                        "Frequência Cardíaca": ["Normal", "Taquicardia", "Bradicardia"]
                    }
                }
                """))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/questions")
    public ResponseEntity<Map<String, Object>> getTriageQuestions() {
        return ResponseEntity.ok(TriageConstants.TRIAGE_QUESTIONS);
    }

    @GetMapping("/by-patient/{patientId}")
    @Operation(
            summary = "Retrieve all triages for a specific patient",
            description = "This endpoint retrieves all triage records for a specified patient by patient ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved triage records for the patient",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<TriageOutput>> findTriagesByPatient(
            @PathVariable("patientId") Integer patientId) {
        List<TriageOutput> triages = findTriagesByPatientIdUseCase.execute(patientId);
        if (triages.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(triages);
    }

    @GetMapping
    @Operation(
            summary = "Retrieve all triages",
            description = "This endpoint returns a paginated list of all triages with the specified sorting options."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of triages",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Page<TriageOutput> findAllTriage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size);
        return findAllTriageUseCase.execute(pageable, sortBy, sortDirection);
    }



}