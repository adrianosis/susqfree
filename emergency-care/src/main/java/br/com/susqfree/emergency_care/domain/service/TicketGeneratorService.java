package br.com.susqfree.emergency_care.domain.service;

import br.com.susqfree.emergency_care.domain.enums.PriorityLevel;
import br.com.susqfree.emergency_care.domain.gateway.AttendanceGateway;
import br.com.susqfree.emergency_care.domain.model.Attendance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketGeneratorService {

    private final AttendanceGateway attendanceGateway;

    public TicketGeneratorService(AttendanceGateway attendanceGateway) {
        this.attendanceGateway = attendanceGateway;
    }

    public String generateTicket(PriorityLevel priorityLevel, Long serviceUnitId) {
        String prefix = priorityLevel.getPrefix();

        List<Attendance> attendances = attendanceGateway.findByServiceUnit(serviceUnitId);

        long lastNumber = attendances.stream()
                .filter(attendance -> attendance.getTicket().startsWith(prefix))
                .mapToLong(attendance -> Long.parseLong(attendance.getTicket().substring(1)))
                .max()
                .orElse(0L);

        long nextNumber = lastNumber + 1;
        return String.format("%s%05d", prefix, nextNumber);
    }
}
