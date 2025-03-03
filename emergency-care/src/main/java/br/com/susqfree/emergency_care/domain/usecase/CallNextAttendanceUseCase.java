package br.com.susqfree.emergency_care.domain.usecase;

import br.com.susqfree.emergency_care.domain.enums.AttendanceStatus;
import br.com.susqfree.emergency_care.domain.gateway.AttendanceGateway;
import br.com.susqfree.emergency_care.domain.model.Attendance;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CallNextAttendanceUseCase {

    private final AttendanceGateway attendanceGateway;

    public CallNextAttendanceUseCase(AttendanceGateway attendanceGateway) {
        this.attendanceGateway = attendanceGateway;
    }

    public Optional<Attendance> execute(Long serviceUnitId) {
        List<String> priorityOrder = List.of("R", "Y", "G", "B");

        for (String prefix : priorityOrder) {
            List<Attendance> attendances = attendanceGateway.findByServiceUnitAndStatus(serviceUnitId, AttendanceStatus.AWAITING_ATTENDANCE);

            Optional<Attendance> nextInPriority = attendances.stream()
                    .filter(attendance -> attendance.getTicket().startsWith(prefix))
                    .findFirst();

            if (nextInPriority.isPresent()) {
                Attendance next = nextInPriority.get();
                return Optional.of(attendanceGateway.save(new Attendance(
                        next.getId(),
                        next.getPatientId(),
                        next.getServiceUnit(),
                        AttendanceStatus.IN_ATTENDANCE,
                        next.getTicket()
                )));
            }
        }
        return Optional.empty();
    }
}
