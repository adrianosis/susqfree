package br.com.susqfree.schedule_management.api.controller;

import br.com.susqfree.schedule_management.domain.input.ConfirmAppointmentInput;
import br.com.susqfree.schedule_management.domain.input.ScheduleAppointmentInput;
import br.com.susqfree.schedule_management.domain.output.AppointmentOutput;
import br.com.susqfree.schedule_management.domain.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    @PostMapping
    public ResponseEntity<AppointmentOutput> schedule(@RequestBody ScheduleAppointmentInput inputs) {
        var output = scheduleAppointmentUseCase.execute(inputs);

        return ResponseEntity.ok(output);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<AppointmentOutput> cancel(@PathVariable UUID appointmentId) {
        var output = cancelAppointmentUseCase.execute(appointmentId);

        return ResponseEntity.ok(output);
    }

    @PutMapping("/confirm")
    public ResponseEntity<AppointmentOutput> confirm(@RequestBody ConfirmAppointmentInput input) {
        var output = confirmAppointmentUsecase.execute(input);

        return ResponseEntity.ok(output);
    }

    @PutMapping("/{appointmentId}/complete")
    public ResponseEntity<AppointmentOutput> confirm(@PathVariable UUID appointmentId) {
        var output = completeAppointmentUsecase.execute(appointmentId);

        return ResponseEntity.ok(output);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentOutput>> findByPatientId(@PathVariable UUID patientId,
                                                                   @RequestParam LocalDateTime startDateTime,
                                                                   @RequestParam LocalDateTime endDateTime) {
        var output = findAppointmentsByPatientUseCase.execute(patientId, startDateTime, endDateTime);

        return ResponseEntity.ok(output);
    }

    @GetMapping("/health-unit/{healthUnitId}")
    public ResponseEntity<List<AppointmentOutput>> findByHealthUnitId(@PathVariable long healthUnitId,
                                                                      @RequestParam LocalDateTime startDateTime,
                                                                      @RequestParam LocalDateTime endDateTime,
                                                                      Pageable pageable) {
        var output = findAppointmentsByHealthUnitUseCase.execute(healthUnitId, startDateTime, endDateTime, pageable);

        return ResponseEntity.ok(output);
    }

    @GetMapping("/available")
    public ResponseEntity<Page<AppointmentOutput>> findAvailable(@RequestParam long healthUnitId,
                                                                 @RequestParam long specialtyId,
                                                                 Pageable pageable) {
        var output = findAppointmentsAvailableByHealthUnitAndSpecialtyUseCase.execute(healthUnitId, specialtyId, pageable);

        return ResponseEntity.ok(output);
    }

}
