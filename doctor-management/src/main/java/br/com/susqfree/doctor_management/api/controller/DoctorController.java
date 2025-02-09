package br.com.susqfree.doctor_management.api.controller;

import br.com.susqfree.doctor_management.api.dto.DoctorInput;
import br.com.susqfree.doctor_management.api.dto.DoctorOutput;
import br.com.susqfree.doctor_management.api.mapper.DoctorDtoMapper;
import br.com.susqfree.doctor_management.domain.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@Tag(name = "Doctors", description = "Endpoints para gerenciamento de médicos")
public class DoctorController {

    private final CreateDoctorUseCase createDoctorUseCase;
    private final UpdateDoctorUseCase updateDoctorUseCase;
    private final DeleteDoctorUseCase deleteDoctorUseCase;
    private final FindDoctorByIdUseCase findDoctorByIdUseCase;
    private final FindDoctorsBySpecialtyUseCase findDoctorsBySpecialtyUseCase;

    public DoctorController(
            CreateDoctorUseCase createDoctorUseCase,
            UpdateDoctorUseCase updateDoctorUseCase,
            DeleteDoctorUseCase deleteDoctorUseCase,
            FindDoctorByIdUseCase findDoctorByIdUseCase,
            FindDoctorsBySpecialtyUseCase findDoctorsBySpecialtyUseCase) {
        this.createDoctorUseCase = createDoctorUseCase;
        this.updateDoctorUseCase = updateDoctorUseCase;
        this.deleteDoctorUseCase = deleteDoctorUseCase;
        this.findDoctorByIdUseCase = findDoctorByIdUseCase;
        this.findDoctorsBySpecialtyUseCase = findDoctorsBySpecialtyUseCase;
    }

    @Operation(
            summary = "Registrar Médico",
            description = "Registra um novo médico no sistema com suas informações.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DoctorInput.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de Registro de Médico",
                                    summary = "Dados para registrar um novo médico",
                                    value = "{ \"name\": \"Dr. Carlos Silva\", \"crm\": \"123456-SP\", \"phone\": \"(11) 91234-5678\", \"email\": \"carlos.silva@exemplo.com\", \"specialtyIds\": [1, 2] }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Médico criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
            }
    )
    @PostMapping
    public ResponseEntity<DoctorOutput> create(@RequestBody DoctorInput input) {
        DoctorOutput createdDoctor = DoctorDtoMapper.toOutput(createDoctorUseCase.execute(DoctorDtoMapper.toDomain(input, null)));
        return ResponseEntity.status(201).body(createdDoctor);
    }

    @Operation(
            summary = "Atualizar Médico",
            description = "Atualiza as informações de um médico existente.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DoctorInput.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de Atualização de Médico",
                                    summary = "Dados para atualizar um médico",
                                    value = "{ \"name\": \"Dr. Ana Souza\", \"crm\": \"654321-SP\", \"phone\": \"(21) 99876-5432\", \"email\": \"ana.souza@exemplo.com\", \"specialtyIds\": [3, 4] }"
                            )
                    )
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<DoctorOutput> update(@PathVariable Long id, @RequestBody DoctorInput input) {
        DoctorOutput updatedDoctor = DoctorDtoMapper.toOutput(updateDoctorUseCase.execute(DoctorDtoMapper.toDomain(input, id)));
        return ResponseEntity.ok(updatedDoctor);
    }

    @Operation(
            summary = "Buscar Médico por ID",
            description = "Retorna as informações de um médico com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Médico encontrado"),
                    @ApiResponse(responseCode = "404", description = "Médico não encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<DoctorOutput> findById(@PathVariable Long id) {
        return findDoctorByIdUseCase.execute(id)
                .map(doctor -> ResponseEntity.ok(DoctorDtoMapper.toOutput(doctor)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Buscar Médicos por Especialidade",
            description = "Retorna uma lista de médicos que possuem a especialidade fornecida.",
            responses = @ApiResponse(responseCode = "200", description = "Lista de médicos retornada com sucesso")
    )
    @GetMapping("/specialty/{name}")
    public ResponseEntity<List<DoctorOutput>> findBySpecialty(@PathVariable String name) {
        List<DoctorOutput> doctors = findDoctorsBySpecialtyUseCase.execute(name).stream()
                .map(DoctorDtoMapper::toOutput)
                .toList();
        return ResponseEntity.ok(doctors);
    }

    @Operation(
            summary = "Deletar Médico",
            description = "Remove um médico existente do sistema com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Médico removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Médico não encontrado")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteDoctorUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
