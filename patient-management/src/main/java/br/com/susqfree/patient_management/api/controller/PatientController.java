package br.com.susqfree.patient_management.api.controller;

import br.com.susqfree.patient_management.domain.input.CreatePatientInput;
import br.com.susqfree.patient_management.domain.input.UpdatePatientInput;
import br.com.susqfree.patient_management.domain.output.PatientOutput;
import br.com.susqfree.patient_management.domain.usecase.*;
import br.com.susqfree.patient_management.infra.config.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
@Tag(name = "Patient", description = "Operations related to patient management")
public class PatientController {

    private final CreatePatientUseCase createPatientUseCase;
    private final UpdatePatientUseCase updatePatientUseCase;
    private final FindPatientByIdUseCase findPatientByIdUseCase;
    private final FindPatientByCpfUseCase findPatientByCpfUseCase;
    private final FindAllPatientsUseCase findAllPatientsUseCase;
    private final SecurityUtils securityUtils;

    @Operation(summary = "Create a new patient",
            description = "Create a new patient and return the created patient details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreatePatientInput.class),
                            examples = @ExampleObject(
                                    name = "Example of Patient Create",
                                    summary = "Example request body for create a patient",
                                    value = "{\"name\":\"Julia\",\"gender\":\"F\",\"birthDate\":\"2005-06-24\",\"cpf\":\"11133355500\",\"susNumber\":\"123412341234\",\"phoneNumber\":\"11999995555\",\"mail\":\"julia@test.com\",\"street\":\"Av. Paulista\",\"number\":\"1400\",\"complement\":null,\"district\":\"Bela Vista\",\"city\":\"São Paulo\",\"state\":\"SP\",\"postalCode\":\"12345678\",\"longitude\":-46.654887,\"latitude\":-23.562642}"
                            )
                    )
            )
    )
    @PostMapping
    public ResponseEntity<PatientOutput> create(@RequestBody @Valid CreatePatientInput input) {
        UUID patientId = UUID.fromString(securityUtils.getUserName());
        var output = createPatientUseCase.execute(input, patientId);

        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Update a existing patient",
            description = "Update a existing patient and return the updated patient details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreatePatientInput.class),
                            examples = @ExampleObject(
                                    name = "Example of Patient Update",
                                    summary = "Example request body for update a patient",
                                    value = "{\"name\":\"Julia Silva\",\"gender\":\"O\",\"phoneNumber\":\"11999993333\",\"mail\":\"julia.silva@test.com\",\"street\":\"Rua Capitão\",\"number\":\"1450\",\"complement\":null,\"district\":\"Limões\",\"city\":\"Santa Catarina\",\"state\":\"SC\",\"postalCode\":\"22224444\",\"longitude\":-46.654888,\"latitude\":-23.562643}"
                            )
                    )
            )
    )
    @PutMapping("/{patientId}")
    @PreAuthorize("hasRole('ADMIN') or #patientId.toString().equals(authentication.name)")
    public ResponseEntity<PatientOutput> update(@PathVariable UUID patientId,
                                                @RequestBody @Valid UpdatePatientInput input) {
        var output = updatePatientUseCase.execute(patientId, input);

        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Find Patient by ID", description = "Retrieve a patient by ID")
    @GetMapping("/{patientId}")
    @PreAuthorize("hasRole('ADMIN') or #patientId.toString().equals(authentication.name)")
    public ResponseEntity<PatientOutput> findById(@PathVariable UUID patientId) {
        var output = findPatientByIdUseCase.execute(patientId);

        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Find Patient by CPF", description = "Retrieve a patient by CPF")
    @GetMapping("/cpf/{cpf}")
    @PostAuthorize("hasRole('ADMIN') or returnObject.body.id.toString().equals(authentication.name)")
    public ResponseEntity<PatientOutput> findByCpf(@PathVariable String cpf) {
        var output = findPatientByCpfUseCase.execute(cpf);

        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Find All Patient", description = "Retrieve all patients")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<PatientOutput>> findAll(Pageable pageable) {
        var output = findAllPatientsUseCase.execute(pageable);

        return ResponseEntity.ok(output);
    }

}
