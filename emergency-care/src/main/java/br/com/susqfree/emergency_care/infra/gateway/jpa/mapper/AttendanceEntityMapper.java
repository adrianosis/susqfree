package br.com.susqfree.emergency_care.infra.gateway.jpa.mapper;

import br.com.susqfree.emergency_care.domain.model.Attendance;
import br.com.susqfree.emergency_care.infra.gateway.jpa.entity.AttendanceEntity;
import org.springframework.stereotype.Component;

@Component
public class AttendanceEntityMapper {

    private final ServiceUnitEntityMapper serviceUnitEntityMapper;

    public AttendanceEntityMapper(ServiceUnitEntityMapper serviceUnitEntityMapper) {
        this.serviceUnitEntityMapper = serviceUnitEntityMapper;
    }

    public Attendance toDomain(AttendanceEntity entity) {
        return new Attendance(
                entity.getId(),
                entity.getPatientId(),
                serviceUnitEntityMapper.toDomain(entity.getServiceUnit()),
                entity.getStatus(),
                entity.getTicket()
        );
    }

    public AttendanceEntity toEntity(Attendance domain) {
        return new AttendanceEntity(
                domain.getId(),
                domain.getPatientId(),
                serviceUnitEntityMapper.toEntity(domain.getServiceUnit()),
                domain.getStatus(),
                domain.getTicket()
        );
    }
}
