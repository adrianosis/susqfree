package br.com.susqfree.emergency_care.domain.gateway;

import br.com.susqfree.emergency_care.domain.enums.AttendanceStatus;
import br.com.susqfree.emergency_care.domain.model.Attendance;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttendanceGateway {

    Attendance save(Attendance attendance);

    Optional<Attendance> findById(Long id);

    List<Attendance> findByStatusOrderByIdAsc(AttendanceStatus status);

    List<Attendance> findByServiceUnitAndStatus(Long serviceUnitId, AttendanceStatus status);

    List<Attendance> findByServiceUnit(Long serviceUnitId);

    boolean existsByPatientId(UUID patientId);

    void delete(Attendance attendance);
}
