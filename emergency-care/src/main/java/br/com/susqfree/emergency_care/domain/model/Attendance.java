package br.com.susqfree.emergency_care.domain.model;

import br.com.susqfree.emergency_care.domain.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Attendance {

    private final Long id;
    private final UUID patientId;
    private final ServiceUnit serviceUnit;
    private final AttendanceStatus status;
    private final String ticket;
}
