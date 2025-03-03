package br.com.susqfree.emergency_care.api.controller;

import br.com.susqfree.emergency_care.api.dto.AttendanceInput;
import br.com.susqfree.emergency_care.api.dto.AttendanceOutput;
import br.com.susqfree.emergency_care.api.mapper.AttendanceDtoMapper;
import br.com.susqfree.emergency_care.domain.enums.AttendanceStatus;
import br.com.susqfree.emergency_care.domain.enums.PriorityLevel;
import br.com.susqfree.emergency_care.domain.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
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


    @PostMapping
    @Operation(summary = "Register Attendance", description = "Registers a new patient attendance with priority level")
    public ResponseEntity<AttendanceOutput> create(@RequestBody AttendanceInput input) {
        PriorityLevel priority = PriorityLevel.valueOf(input.getPriorityLevel().toUpperCase());
        var attendance = createAttendanceUseCase.execute(input.getPatientId(), input.getServiceUnitId(), priority);
        return ResponseEntity.status(201).body(mapper.toOutput(attendance));
    }

    @Operation(summary = "Call Next Attendance", description = "Calls the next patient in the queue for a specific service")
    @PostMapping("/call-next/{serviceUnitId}")
    public ResponseEntity<AttendanceOutput> callNext(@PathVariable Long serviceUnitId) {
        Optional<AttendanceOutput> next = callNextAttendanceUseCase.execute(serviceUnitId)
                .map(mapper::toOutput);
        return next.map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }

    @Operation(summary = "Complete Attendance", description = "Marks an attendance as completed and moves it to history")
    @DeleteMapping("/{id}/complete")
    public ResponseEntity<Void> completeAttendance(@PathVariable Long id) {
        completeAttendanceUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cancel Attendance", description = "Cancels an attendance and moves it to history")
    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelAttendance(@PathVariable Long id) {
        cancelAttendanceUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

}
