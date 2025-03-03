package br.com.susqfree.emergency_care.infra.gateway.jpa;

import br.com.susqfree.emergency_care.domain.gateway.AttendanceHistoryGateway;
import br.com.susqfree.emergency_care.domain.model.AttendanceHistory;
import br.com.susqfree.emergency_care.infra.gateway.jpa.mapper.AttendanceHistoryEntityMapper;
import br.com.susqfree.emergency_care.infra.gateway.jpa.repository.AttendanceHistoryRepository;
import org.springframework.stereotype.Component;

@Component
public class AttendanceHistoryJpaGateway implements AttendanceHistoryGateway {

    private final AttendanceHistoryRepository repository;
    private final AttendanceHistoryEntityMapper mapper;

    public AttendanceHistoryJpaGateway(AttendanceHistoryRepository repository, AttendanceHistoryEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public AttendanceHistory save(AttendanceHistory attendanceHistory) {
        return mapper.toDomain(repository.save(mapper.toEntity(attendanceHistory)));
    }
}
