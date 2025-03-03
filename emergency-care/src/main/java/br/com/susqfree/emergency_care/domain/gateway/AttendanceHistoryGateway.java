package br.com.susqfree.emergency_care.domain.gateway;

import br.com.susqfree.emergency_care.domain.model.AttendanceHistory;

public interface AttendanceHistoryGateway {

    AttendanceHistory save(AttendanceHistory attendanceHistory);
}
