package br.com.susqfree.emergency_care.api.mapper;

import br.com.susqfree.emergency_care.api.dto.AttendanceOutput;
import br.com.susqfree.emergency_care.domain.model.Attendance;
import org.springframework.stereotype.Component;

@Component
public class AttendanceDtoMapper {

    public AttendanceOutput toOutput(Attendance attendance) {
        return new AttendanceOutput(
                attendance.getId(),
                attendance.getPatientId(),
                attendance.getServiceUnit().getServiceType(),
                attendance.getStatus().name(),
                attendance.getTicket()
        );
    }
}
