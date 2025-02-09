package br.com.susqfree.doctor_management.api.controller;

import br.com.susqfree.doctor_management.api.dto.SpecialtyInput;
import br.com.susqfree.doctor_management.api.dto.SpecialtyOutput;
import br.com.susqfree.doctor_management.api.mapper.SpecialtyDtoMapper;
import br.com.susqfree.doctor_management.domain.model.Specialty;
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
@RequestMapping("/specialties")
@Tag(name = "Specialties", description = "Endpoints para gerenciamento de especialidades médicas")
public class SpecialtyController {

    private final CreateSpecialtyUseCase createSpecialtyUseCase;
    private final UpdateSpecialtyUseCase updateSpecialtyUseCase;
    private final DeleteSpecialtyUseCase deleteSpecialtyUseCase;
    private final FindAllSpecialtiesUseCase findAllSpecialtiesUseCase;

    public SpecialtyController(
            CreateSpecialtyUseCase createSpecialtyUseCase,
            UpdateSpecialtyUseCase updateSpecialtyUseCase,
            DeleteSpecialtyUseCase deleteSpecialtyUseCase,
            FindAllSpecialtiesUseCase findAllSpecialtiesUseCase) {
        this.createSpecialtyUseCase = createSpecialtyUseCase;
        this.updateSpecialtyUseCase = updateSpecialtyUseCase;
        this.deleteSpecialtyUseCase = deleteSpecialtyUseCase;
        this.findAllSpecialtiesUseCase = findAllSpecialtiesUseCase;
    }

    @Operation(
            summary = "Registrar Especialidade",
            description = "Registra uma nova especialidade no sistema com suas informações.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SpecialtyInput.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de Registro de Especialidade",
                                    summary = "Dados para registrar uma nova especialidade",
                                    value = "{ \"name\": \"Cardiologia\", \"description\": \"Especialidade dedicada ao estudo do coração.\" }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Especialidade criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
            }
    )
    @PostMapping
    public ResponseEntity<SpecialtyOutput> create(@RequestBody SpecialtyInput input) {
        Specialty specialty = SpecialtyDtoMapper.toDomain(input, null);
        Specialty createdSpecialty = createSpecialtyUseCase.execute(specialty);
        return ResponseEntity.status(201).body(SpecialtyDtoMapper.toOutput(createdSpecialty));
    }

    @Operation(
            summary = "Atualizar Especialidade",
            description = "Atualiza as informações de uma especialidade existente.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SpecialtyInput.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de Atualização de Especialidade",
                                    summary = "Dados para atualizar uma especialidade",
                                    value = "{ \"name\": \"Neurologia\", \"description\": \"Estudo das doenças do sistema nervoso.\" }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Especialidade atualizada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Especialidade não encontrada")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<SpecialtyOutput> update(@PathVariable Long id, @RequestBody SpecialtyInput input) {
        Specialty specialty = SpecialtyDtoMapper.toDomain(input, id);
        Specialty updatedSpecialty = updateSpecialtyUseCase.execute(specialty);
        return ResponseEntity.ok(SpecialtyDtoMapper.toOutput(updatedSpecialty));
    }

    @Operation(
            summary = "Listar Especialidades",
            description = "Retorna todas as especialidades cadastradas no sistema.",
            responses = @ApiResponse(responseCode = "200", description = "Lista de especialidades retornada com sucesso")
    )
    @GetMapping
    public ResponseEntity<List<SpecialtyOutput>> findAll() {
        List<SpecialtyOutput> specialties = findAllSpecialtiesUseCase.execute().stream()
                .map(SpecialtyDtoMapper::toOutput)
                .toList();
        return ResponseEntity.ok(specialties);
    }

    @Operation(
            summary = "Deletar Especialidade",
            description = "Remove uma especialidade existente do sistema com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Especialidade removida com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Especialidade não encontrada")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteSpecialtyUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
