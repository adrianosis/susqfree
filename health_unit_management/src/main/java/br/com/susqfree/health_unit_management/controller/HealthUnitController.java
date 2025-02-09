package br.com.susqfree.health_unit_management.controller;

import br.com.susqfree.health_unit_management.domain.input.HealthUnitInput;
import br.com.susqfree.health_unit_management.domain.output.HealthUnitOutput;
import br.com.susqfree.health_unit_management.domain.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private final FindHealthUnitByIdUsecase findHealthUnitByIdUsecase;
    private final FindAllHealthUnitsUseCase findAllHealthUnitsUseCase;
    private final DeleteHealthUnitUseCase deleteHealthUnitUseCase;
    private final UpdateHealthUnitUseCase updateHealthUnitUseCase;

    @PostMapping
    @Operation(summary = "Create a new health unit", description = "Create a new health unit and return the created health unit details")
    public ResponseEntity<HealthUnitOutput> create(@RequestBody HealthUnitInput input) {
        var output = createHealthUnitUseCase.execute(input);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get health unit by ID", description = "Retrieve a health unit by its ID")
    public ResponseEntity<HealthUnitOutput> findHealthUnitById(@PathVariable Long id) {
        var output = findHealthUnitByIdUsecase.execute(id);
        return ResponseEntity.ok(output);
    }

    @GetMapping
    @Operation(summary = "Get all health units", description = "Retrieve all health units")
    public ResponseEntity<List<HealthUnitOutput>> findAllHealthUnits() {
        var output = findAllHealthUnitsUseCase.execute();
        return ResponseEntity.ok(output);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a health unit", description = "Delete a health unit by its ID")
    public ResponseEntity<Void> deleteHealthUnit(@PathVariable Long id) {
        deleteHealthUnitUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a health unit", description = "Update a health unit's details by its ID")
    public ResponseEntity<HealthUnitOutput> updateHealthUnit(@PathVariable Long id,
                                                             @RequestBody HealthUnitInput input) {
        var output = updateHealthUnitUseCase.execute(id, input);
        return ResponseEntity.ok(output);
    }
}