package br.com.susqfree.emergency_care.infra.gateway.jpa.repository;

import br.com.susqfree.emergency_care.infra.gateway.jpa.entity.AttendanceHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceHistoryRepository extends JpaRepository<AttendanceHistoryEntity, Long> {
}
