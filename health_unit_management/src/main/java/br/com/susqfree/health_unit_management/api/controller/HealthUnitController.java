package br.com.susqfree.health_unit_management.api.controller;

import br.com.susqfree.health_unit_management.domain.input.HealthUnitInput;
import br.com.susqfree.health_unit_management.domain.output.HealthUnitOutput;
import br.com.susqfree.health_unit_management.domain.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("health-unit")
@Tag(name = "Health Unit", description = "Operations related to health unit management")
public class HealthUnitController {

    private final CreateHealthUnitUseCase createHealthUnitUseCase;
    private final FindHealthUnitByIdUseCase findHealthUnitByIdUsecase;
    private final FindAllHealthUnitsUseCase findAllHealthUnitsUseCase;
    private final UpdateHealthUnitUseCase updateHealthUnitUseCase;

    @PostMapping
    @Operation(summary = "Create a new health unit",
            description = "Create a new health unit and return the created health unit details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HealthUnitInput.class),
                            examples = @ExampleObject(
                                    name = "Example of Health Unit Update",
                                    summary = "Example request body for updating a health unit",
                                    value = "{ \"name\": \"Unidade de Saúde da Familia - Saude Rio\", \"type\": \"UBS\", \"phone\": \"(21) 3312-7580\", \"street\": \"Rua General Polidoro\", \"number\": \"150\", \"complement\": \"Próximo ao Largo do Machado\", \"zipcode\": \"22280-003\", \"city\": \"Rio de Janeiro\", \"state\": \"RJ\", \"latitude\": -22.9284, \"longitude\": -43.1833 }"
                            )
                    )
            )
    )
    public ResponseEntity<HealthUnitOutput> create(@RequestBody @Valid HealthUnitInput input) {
        var output = createHealthUnitUseCase.execute(input);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get health unit by ID", description = "Retrieve a health unit by its ID")
    public ResponseEntity<HealthUnitOutput> findHealthUnitById(@PathVariable @Valid Long id) {
        var output = findHealthUnitByIdUsecase.execute(id);
        return ResponseEntity.ok(output);
    }

    @GetMapping
    @Operation(summary = "Get all health units", description = "Retrieve all health units")
    public ResponseEntity<List<HealthUnitOutput>> findAllHealthUnits() {
        var output = findAllHealthUnitsUseCase.execute();
        return ResponseEntity.ok(output);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a health unit",
            description = "Update a health unit's details by its ID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HealthUnitInput.class),
                            examples = @ExampleObject(
                                    name = "Example of Health Unit Update",
                                    summary = "Example request body for updating a health unit",
                                    value = "{ \"name\": \"Unidade de Pronto Atendimento - UPA 24h de Botafogo\", \"type\": \"UPA\", \"phone\": \"(21) 2334-5160\", \"street\": \"Rua Nelson Mandela\", \"number\": \"100\", \"complement\": \"Proximo ao metro Botafogo\", \"zipcode\": \"22260-002\", \"city\": \"Rio de Janeiro\", \"state\": \"RJ\", \"latitude\": -22.9519, \"longitude\": -43.1825 }"
                            )
                    )
            )
    )
    public ResponseEntity<HealthUnitOutput> updateHealthUnit(@PathVariable Long id,
                                                             @RequestBody @Valid  HealthUnitInput input) {
        var output = updateHealthUnitUseCase.execute(id, input);
        return ResponseEntity.ok(output);
    }
}