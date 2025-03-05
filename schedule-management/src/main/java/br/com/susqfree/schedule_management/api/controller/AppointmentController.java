package br.com.susqfree.schedule_management.api.controller;

import br.com.susqfree.schedule_management.domain.input.ConfirmAppointmentInput;
import br.com.susqfree.schedule_management.domain.input.CreateAppointmentInput;
import br.com.susqfree.schedule_management.domain.input.ScheduleAppointmentInput;
import br.com.susqfree.schedule_management.domain.output.AppointmentOutput;
import br.com.susqfree.schedule_management.domain.usecase.*;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Tag(name = "Appointments", description = "Operations related to schedule management")
@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final ScheduleAppointmentUseCase scheduleAppointmentUseCase;
    private final CancelAppointmentUseCase cancelAppointmentUseCase;
    private final ConfirmAppointmentUseCase confirmAppointmentUsecase;
    private final CompleteAppointmentUseCase completeAppointmentUsecase;

    private final FindAppointmentsByPatientUseCase findAppointmentsByPatientUseCase;
    private final FindAppointmentsAvailableByHealthUnitAndSpecialtyUseCase findAppointmentsAvailableByHealthUnitAndSpecialtyUseCase;
    private final FindAppointmentsByHealthUnitUseCase findAppointmentsByHealthUnitUseCase;

    @Operation(summary = "Schedule new appointment",
            description = "Schedule new appointment and return the scheduled appointment details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateAppointmentInput[].class),
                            examples = @ExampleObject(
                                    name = "Example of appointment Schedule",
                                    summary = "Example request body for schedule a appointment",
                                    value = "{\"appointmentId\":\"3955d804-17c0-427e-935a-4201b81a5825\",\"patientId\":\"afa6c712-7af9-4d93-84d3-656f6770cc7a\"}"
                            )
                    )
            )
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or #inputs.patientId.toString().equals(authentication.name)")
    public ResponseEntity<AppointmentOutput> schedule(@RequestBody @Valid ScheduleAppointmentInput inputs) {
        var output = scheduleAppointmentUseCase.execute(inputs);

        return ResponseEntity.ok(output);
    }


    @Operation(summary = "Cancel an Appointment Schedule",
            description = "Cancel an appointment and return the canceled appointment details")
    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<AppointmentOutput> cancel(@PathVariable UUID appointmentId) {
        var output = cancelAppointmentUseCase.execute(appointmentId);

        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Confirm an appointment",
            description = "Confirm an appointment and return the confirmed appointment details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateAppointmentInput[].class),
                            examples = @ExampleObject(
                                    name = "Example of appointment Confirm",
                                    summary = "Example request body for confirm a appointment",
                                    value = "{\"appointmentId\":\"41e49708-d18a-43cf-8a26-afcec62c9d46\",\"confirmed\":true}"
                            )
                    )
            )
    )
    @PutMapping("/confirm")
    public ResponseEntity<AppointmentOutput> confirm(@RequestBody @Valid ConfirmAppointmentInput input) {
        var output = confirmAppointmentUsecase.execute(input);

        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Complete an Appointment",
            description = "Complete an appointment and return the completed appointment details")
    @PutMapping("/{appointmentId}/complete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppointmentOutput> complete(@PathVariable UUID appointmentId) {
        var output = completeAppointmentUsecase.execute(appointmentId);

        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Find All Appointments by Patient ID and Period", description = "Retrieve all appointments by Patient ID and Period")
    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasRole('ADMIN') or #patientId.toString().equals(authentication.name)")
    public ResponseEntity<List<AppointmentOutput>> findByPatientId(@PathVariable UUID patientId,
                                                                   @RequestParam LocalDateTime startDateTime,
                                                                   @RequestParam LocalDateTime endDateTime) {
        var output = findAppointmentsByPatientUseCase.execute(patientId, startDateTime, endDateTime);

        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Find All Appointments by Health Unit ID and Period", description = "Retrieve all appointments by Health Unit ID and Period")
    @GetMapping("/health-unit/{healthUnitId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<AppointmentOutput>> findByHealthUnitId(@PathVariable long healthUnitId,
                                                                      @RequestParam LocalDateTime startDateTime,
                                                                      @RequestParam LocalDateTime endDateTime,
                                                                      Pageable pageable) {
        var output = findAppointmentsByHealthUnitUseCase.execute(healthUnitId, startDateTime, endDateTime, pageable);

        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Find All Available Appointments by Health Unit ID and Specialty ID",
            description = "Retrieve all available appointments by Health Unit ID and Specialty ID")
    @GetMapping("/available")
    public ResponseEntity<Page<AppointmentOutput>> findAvailable(@RequestParam long healthUnitId,
                                                                 @RequestParam long specialtyId,
                                                                 Pageable pageable) {
        var output = findAppointmentsAvailableByHealthUnitAndSpecialtyUseCase.execute(healthUnitId, specialtyId, pageable);

        return ResponseEntity.ok(output);
    }

}
