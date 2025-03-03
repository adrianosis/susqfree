package br.com.susqfree.emergency_care.infra.gateway.jpa.repository;

import br.com.susqfree.emergency_care.domain.enums.AttendanceStatus;
import br.com.susqfree.emergency_care.infra.gateway.jpa.entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {

    List<AttendanceEntity> findByStatusOrderByIdAsc(AttendanceStatus status);

    List<AttendanceEntity> findByServiceUnitIdAndStatusOrderByIdAsc(Long serviceUnitId, AttendanceStatus status);

    List<AttendanceEntity> findByServiceUnitId(Long serviceUnitId);

    boolean existsByPatientId(UUID patientId);

}
