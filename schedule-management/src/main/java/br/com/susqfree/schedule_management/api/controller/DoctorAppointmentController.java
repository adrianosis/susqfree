package br.com.susqfree.schedule_management.api.controller;

import br.com.susqfree.schedule_management.domain.input.CancelDoctorAppointmentsInput;
import br.com.susqfree.schedule_management.domain.input.CreateAppointmentInput;
import br.com.susqfree.schedule_management.domain.output.AppointmentOutput;
import br.com.susqfree.schedule_management.domain.usecase.CancelDoctorAppointmentsUseCase;
import br.com.susqfree.schedule_management.domain.usecase.CreateDoctorAppointmentsUseCase;
import br.com.susqfree.schedule_management.domain.usecase.FindAppointmentsByDoctorUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Doctor Appointments", description = "Operations related to schedule management")
@RestController
@RequestMapping("/api/appointments/doctor")
@RequiredArgsConstructor
public class DoctorAppointmentController {

    private final CreateDoctorAppointmentsUseCase createDoctorAppointmentsUseCase;
    private final CancelDoctorAppointmentsUseCase cancelDoctorAppointmentsUseCase;
    private final FindAppointmentsByDoctorUseCase findAppointmentsByDoctorUseCase;

    @Operation(summary = "Create new appointments",
            description = "Create new appointments and return the created appointments details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateAppointmentInput[].class),
                            examples = @ExampleObject(
                                    name = "Example of appointments Create",
                                    summary = "Example request body for create a appointments",
                                    value = "{\"doctorId\":1,\"healthUnitId\":1,\"specialtyId\":1,\"periods\": [[{\"startDateTime\":\"2025-02-24T08:00:00\",\"endDateTime\":\"2025-02-24T12:00:00\"}]}"
                            )
                    )
            )
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AppointmentOutput>> createDoctorAppointments(@RequestBody @Valid CreateAppointmentInput input) {
        var output = createDoctorAppointmentsUseCase.execute(input);

        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Cancel existing appointments",
            description = "Cancel a existing appointments and return the updated appointments details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CancelDoctorAppointmentsInput.class),
                            examples = @ExampleObject(
                                    name = "Example of appointments Cancel",
                                    summary = "Example request body for cancel a appointments",
                                    value = "{\"doctorId\":1,\"startDateTime\":\"2025-03-01T08:00:00\",\"endDateTime\":\"2025-03-01T18:00:00\",\"justification\":\"justification\"}"
                            )
                    )
            )
    )
    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AppointmentOutput>> cancelDoctorAppointments(@RequestBody @Valid CancelDoctorAppointmentsInput input) {
        var output = cancelDoctorAppointmentsUseCase.execute(input);

        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Find All Appointments by Doctor ID and Period", description = "Retrieve all appointments by Doctor ID and Period")
    @GetMapping("/{doctorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AppointmentOutput>> findAppointmentsByDoctorId(@PathVariable long doctorId,
                                                                              @RequestParam LocalDateTime startDateTime,
                                                                              @RequestParam LocalDateTime endDateTime) {
        var output = findAppointmentsByDoctorUseCase.execute(doctorId, startDateTime, endDateTime);

        return ResponseEntity.ok(output);
    }

}