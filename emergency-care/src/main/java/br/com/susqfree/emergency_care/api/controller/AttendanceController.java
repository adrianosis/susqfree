package br.com.susqfree.emergency_care.api.controller;

import br.com.susqfree.emergency_care.api.dto.AttendanceOutput;
import br.com.susqfree.emergency_care.api.mapper.AttendanceDtoMapper;
import br.com.susqfree.emergency_care.domain.model.TriageInput;
import br.com.susqfree.emergency_care.domain.usecase.CallNextAttendanceUseCase;
import br.com.susqfree.emergency_care.domain.usecase.CancelAttendanceUseCase;
import br.com.susqfree.emergency_care.domain.usecase.CompleteAttendanceUseCase;
import br.com.susqfree.emergency_care.domain.usecase.CreateAttendanceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/attendances")
@Tag(name = "Attendances", description = "Endpoints for managing patient attendance")
public class AttendanceController {

    private final CreateAttendanceUseCase createAttendanceUseCase;
    private final CallNextAttendanceUseCase callNextAttendanceUseCase;
    private final AttendanceDtoMapper mapper;
    private final CompleteAttendanceUseCase completeAttendanceUseCase;
    private final CancelAttendanceUseCase cancelAttendanceUseCase;

    public AttendanceController(
            CreateAttendanceUseCase createAttendanceUseCase,
            CallNextAttendanceUseCase callNextAttendanceUseCase,
            CompleteAttendanceUseCase completeAttendanceUseCase,
            CancelAttendanceUseCase cancelAttendanceUseCase,
            AttendanceDtoMapper mapper
    ) {
        this.createAttendanceUseCase = createAttendanceUseCase;
        this.callNextAttendanceUseCase = callNextAttendanceUseCase;
        this.completeAttendanceUseCase = completeAttendanceUseCase;
        this.cancelAttendanceUseCase = cancelAttendanceUseCase;
        this.mapper = mapper;
    }

    @PostMapping("/{serviceUnitId}")
    @Operation(
            summary = "Register Attendance",
            description = "Registers a new patient attendance based on the triage process. The request body contains patient information, and the system will determine the priority level.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Attendance successfully registered",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    name = "Emergency",
                                                    value = """
                                                            {
                                                                "id": 1,
                                                                "patientId": "328bcfaa-83af-47cc-a732-f9e44e0d7600",
                                                                "serviceUnitId": 1,
                                                                "status": "AWAITING_ATTENDANCE",
                                                                "ticket": "R00001"
                                                            }
                                                            """
                                            ),
                                            @ExampleObject(
                                                    name = "Urgent",
                                                    value = """
                                                            {
                                                                "id": 2,
                                                                "patientId": "b3ac69c4-1b12-493c-85a2-12e4c4b1b67d",
                                                                "serviceUnitId": 2,
                                                                "status": "AWAITING_ATTENDANCE",
                                                                "ticket": "Y00002"
                                                            }
                                                            """
                                            ),
                                            @ExampleObject(
                                                    name = "Priority",
                                                    value = """
                                                            {
                                                                "id": 3,
                                                                "patientId": "4c37fa2e-93d3-4fb0-b785-2d2b09a4d10c",
                                                                "serviceUnitId": 3,
                                                                "status": "AWAITING_ATTENDANCE",
                                                                "ticket": "G00003"
                                                            }
                                                            """
                                            ),
                                            @ExampleObject(
                                                    name = "Non-priority",
                                                    value = """
                                                            {
                                                                "id": 4,
                                                                "patientId": "a23e4567-e89b-12d3-a456-426614174000",
                                                                "serviceUnitId": 4,
                                                                "status": "AWAITING_ATTENDANCE",
                                                                "ticket": "B00004"
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
                                                                "message": "The field 'patientId' is required."
                                                            }
                                                            """
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Patient or Service Unit not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    name = "Patient not found",
                                                    value = """
                                                            {
                                                                "message": "Paciente com ID 328bcfaa-83af-47cc-a732-f9e44e0d7600 não encontrado."
                                                            }
                                                            """
                                            ),
                                            @ExampleObject(
                                                    name = "Service Unit not found",
                                                    value = """
                                                            {
                                                                "message": "Unidade de serviço não encontrada."
                                                            }
                                                            """
                                            )
                                    }
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN') or #input.patientId.toString().equals(authentication.name)")
    public ResponseEntity<AttendanceOutput> create(@PathVariable Long serviceUnitId,
                                                   @RequestBody TriageInput input) {
        var attendance = createAttendanceUseCase.execute(input.patientId(), serviceUnitId, input);
        return ResponseEntity.status(201).body(mapper.toOutput(attendance));
    }

    @Operation(summary = "Call Next Attendance", description = "Calls the next patient in the queue for a specific service")
    @PostMapping("/call-next/{serviceUnitId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AttendanceOutput> callNext(@PathVariable Long serviceUnitId) {
        Optional<AttendanceOutput> next = callNextAttendanceUseCase.execute(serviceUnitId)
                .map(mapper::toOutput);
        return next.map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }

    @Operation(summary = "Complete Attendance", description = "Marks an attendance as completed and moves it to history")
    @PutMapping("/{id}/complete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> completeAttendance(@PathVariable Long id) {
        completeAttendanceUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cancel Attendance", description = "Cancels an attendance and moves it to history")
    @DeleteMapping("/{id}/cancel")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> cancelAttendance(@PathVariable Long id) {
        cancelAttendanceUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

}
