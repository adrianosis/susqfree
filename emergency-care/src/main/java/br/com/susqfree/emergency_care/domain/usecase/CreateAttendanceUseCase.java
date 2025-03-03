package br.com.susqfree.emergency_care.domain.usecase;

import br.com.susqfree.emergency_care.config.exception.PatientAlreadyInQueueException;
import br.com.susqfree.emergency_care.config.exception.PatientNotFoundException;
import br.com.susqfree.emergency_care.config.exception.ServiceUnitNotFoundException;
import br.com.susqfree.emergency_care.domain.enums.AttendanceStatus;
import br.com.susqfree.emergency_care.domain.enums.PriorityLevel;
import br.com.susqfree.emergency_care.domain.gateway.AttendanceGateway;
import br.com.susqfree.emergency_care.domain.gateway.PatientGateway;
import br.com.susqfree.emergency_care.domain.gateway.ServiceUnitGateway;
import br.com.susqfree.emergency_care.domain.model.Attendance;
import br.com.susqfree.emergency_care.domain.model.Patient;
import br.com.susqfree.emergency_care.domain.model.ServiceUnit;
import br.com.susqfree.emergency_care.domain.service.TicketGeneratorService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CreateAttendanceUseCase {

    private final AttendanceGateway attendanceGateway;
    private final ServiceUnitGateway serviceUnitGateway;
    private final TicketGeneratorService ticketGeneratorService;
    private final PatientGateway patientGateway;

    public CreateAttendanceUseCase(
            AttendanceGateway attendanceGateway,
            ServiceUnitGateway serviceUnitGateway,
            TicketGeneratorService ticketGeneratorService,
            PatientGateway patientGateway
    ) {
        this.attendanceGateway = attendanceGateway;
        this.serviceUnitGateway = serviceUnitGateway;
        this.ticketGeneratorService = ticketGeneratorService;
        this.patientGateway = patientGateway;
    }

    public Attendance execute(UUID patientId, Long serviceUnitId, PriorityLevel priorityLevel) {
        Optional<Patient> patient = patientGateway.findById(patientId);
        if (patient.isEmpty()) {
            throw new PatientNotFoundException("Paciente com ID " + patientId + " não encontrado.");
        }

        Optional<ServiceUnit> serviceUnit = serviceUnitGateway.findById(serviceUnitId);
        if (serviceUnit.isEmpty()) {
            throw new ServiceUnitNotFoundException("Service unit not found");
        }

        boolean alreadyInQueue = attendanceGateway.existsByPatientId(patientId);
        if (alreadyInQueue) {
            throw new PatientAlreadyInQueueException();
        }

        String ticket = ticketGeneratorService.generateTicket(priorityLevel, serviceUnitId);

        Attendance newAttendance = new Attendance(
                null,
                patientId,
                serviceUnit.get(),
                AttendanceStatus.AWAITING_ATTENDANCE,
                ticket
        );

        return attendanceGateway.save(newAttendance);
    }
}
