package br.com.susqfree.emergency_care.infra.gateway.jpa.mapper;

import br.com.susqfree.emergency_care.domain.model.AttendanceHistory;
import br.com.susqfree.emergency_care.infra.gateway.jpa.entity.AttendanceHistoryEntity;
import org.springframework.stereotype.Component;

@Component
public class AttendanceHistoryEntityMapper {

    private final ServiceUnitEntityMapper serviceUnitEntityMapper;

    public AttendanceHistoryEntityMapper(ServiceUnitEntityMapper serviceUnitEntityMapper) {
        this.serviceUnitEntityMapper = serviceUnitEntityMapper;
    }

    public AttendanceHistoryEntity toEntity(AttendanceHistory history) {
        return new AttendanceHistoryEntity(
                history.getId(),
                history.getPatientId(),
                serviceUnitEntityMapper.toEntity(history.getServiceUnit()),
                history.getStatus()
        );
    }

    public AttendanceHistory toDomain(AttendanceHistoryEntity entity) {
        return new AttendanceHistory(
                entity.getId(),
                entity.getPatientId(),
                serviceUnitEntityMapper.toDomain(entity.getServiceUnit()),
                entity.getStatus()
        );
    }
}
