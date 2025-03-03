package br.com.susqfree.emergency_care.domain.usecase;

import br.com.susqfree.emergency_care.config.exception.AttendanceNotFoundException;
import br.com.susqfree.emergency_care.domain.enums.AttendanceStatus;
import br.com.susqfree.emergency_care.domain.gateway.AttendanceGateway;
import br.com.susqfree.emergency_care.domain.gateway.AttendanceHistoryGateway;
import br.com.susqfree.emergency_care.domain.model.Attendance;
import br.com.susqfree.emergency_care.domain.model.AttendanceHistory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CancelAttendanceUseCase {

    private final AttendanceGateway attendanceGateway;
    private final AttendanceHistoryGateway attendanceHistoryGateway;

    public CancelAttendanceUseCase(AttendanceGateway attendanceGateway, AttendanceHistoryGateway attendanceHistoryGateway) {
        this.attendanceGateway = attendanceGateway;
        this.attendanceHistoryGateway = attendanceHistoryGateway;
    }

    public void execute(Long attendanceId) {
        Optional<Attendance> attendance = attendanceGateway.findById(attendanceId);
        if (attendance.isEmpty()) {
            throw new AttendanceNotFoundException("Attendance not found");
        }

        Attendance cancelledAttendance = new Attendance(
                attendance.get().getId(),
                attendance.get().getPatientId(),
                attendance.get().getServiceUnit(),
                AttendanceStatus.CANCELLED,
                attendance.get().getTicket()
        );

        attendanceGateway.save(cancelledAttendance);

        AttendanceHistory history = new AttendanceHistory(
                null,
                cancelledAttendance.getPatientId(),
                cancelledAttendance.getServiceUnit(),
                cancelledAttendance.getStatus()
        );

        attendanceHistoryGateway.save(history);
        attendanceGateway.delete(cancelledAttendance);
    }
}
