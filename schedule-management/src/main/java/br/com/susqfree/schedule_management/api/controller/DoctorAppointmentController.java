package br.com.susqfree.schedule_management.api.controller;

import br.com.susqfree.schedule_management.domain.input.CancelDoctorAppointmentsInput;
import br.com.susqfree.schedule_management.domain.input.CreateAppointmentInput;
import br.com.susqfree.schedule_management.domain.output.AppointmentOutput;
import br.com.susqfree.schedule_management.domain.usecase.CancelDoctorAppointmentsUseCase;
import br.com.susqfree.schedule_management.domain.usecase.CreateDoctorAppointmentsUseCase;
import br.com.susqfree.schedule_management.domain.usecase.FindAppointmentsByDoctorUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments/doctor")
@RequiredArgsConstructor
public class DoctorAppointmentController {

    private final CreateDoctorAppointmentsUseCase createDoctorAppointmentsUseCase;
    private final CancelDoctorAppointmentsUseCase cancelDoctorAppointmentsUseCase;
    private final FindAppointmentsByDoctorUseCase findAppointmentsByDoctorUseCase;

    @PostMapping
    public ResponseEntity<List<AppointmentOutput>> createDoctorAppointments(@RequestBody List<CreateAppointmentInput> inputs) {
        var output = createDoctorAppointmentsUseCase.execute(inputs);

        return ResponseEntity.ok(output);
    }

    @DeleteMapping
    public ResponseEntity<List<AppointmentOutput>> cancelDoctorAppointments(@RequestBody CancelDoctorAppointmentsInput input) {
        var output = cancelDoctorAppointmentsUseCase.execute(input);

        return ResponseEntity.ok(output);
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<List<AppointmentOutput>> cancelDoctorAppointments(@PathVariable long doctorId,
                                                                            @RequestParam LocalDateTime startDateTime,
                                                                            @RequestParam LocalDateTime endDateTime) {
        var output = findAppointmentsByDoctorUseCase.execute(doctorId, startDateTime, endDateTime);

        return ResponseEntity.ok(output);
    }

}
