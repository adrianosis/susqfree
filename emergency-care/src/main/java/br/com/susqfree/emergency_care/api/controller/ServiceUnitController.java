package br.com.susqfree.emergency_care.api.controller;

import br.com.susqfree.emergency_care.api.dto.ServiceUnitInput;
import br.com.susqfree.emergency_care.api.dto.ServiceUnitOutput;
import br.com.susqfree.emergency_care.api.mapper.ServiceUnitDtoMapper;
import br.com.susqfree.emergency_care.domain.usecase.CreateServiceUnitUseCase;
import br.com.susqfree.emergency_care.domain.usecase.FindServiceUnitByIdUseCase;
import br.com.susqfree.emergency_care.domain.usecase.ListAllServiceUnitsByUnitIdUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service-units")
@Tag(name = "Service Units", description = "Endpoints for managing service units")
public class ServiceUnitController {

    private final CreateServiceUnitUseCase createServiceUnitUseCase;
    private final FindServiceUnitByIdUseCase findServiceUnitByIdUseCase;
    private final ListAllServiceUnitsByUnitIdUseCase listAllServiceUnitsByUnitIdUseCase;
    private final ServiceUnitDtoMapper mapper;

    public ServiceUnitController(
            CreateServiceUnitUseCase createServiceUnitUseCase,
            FindServiceUnitByIdUseCase findServiceUnitByIdUseCase,
            ListAllServiceUnitsByUnitIdUseCase listAllServiceUnitsByUnitIdUseCase,
            ServiceUnitDtoMapper mapper
    ) {
        this.createServiceUnitUseCase = createServiceUnitUseCase;
        this.findServiceUnitByIdUseCase = findServiceUnitByIdUseCase;
        this.listAllServiceUnitsByUnitIdUseCase = listAllServiceUnitsByUnitIdUseCase;
        this.mapper = mapper;
    }

    @Operation(summary = "Register Service Unit", description = "Registers a new service unit")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceUnitOutput> create(@RequestBody ServiceUnitInput input) {
        var serviceUnit = createServiceUnitUseCase.execute(mapper.toDomain(input));
        return ResponseEntity.status(201).body(mapper.toOutput(serviceUnit));
    }

    @Operation(summary = "Find Service Unit by ID", description = "Finds a service unit by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ServiceUnitOutput> findById(@PathVariable Long id) {
        return findServiceUnitByIdUseCase.execute(id)
                .map(unit -> ResponseEntity.ok(mapper.toOutput(unit)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "List All Service Units", description = "Returns a list of all registered service units")
    @GetMapping("/health-unit/{unitId}")
    public ResponseEntity<List<ServiceUnitOutput>> findAllByUnitId(@PathVariable Long unitId) {
        var units = listAllServiceUnitsByUnitIdUseCase.execute(unitId)
                .stream()
                .map(mapper::toOutput)
                .toList();
        return ResponseEntity.ok(units);
    }

}
