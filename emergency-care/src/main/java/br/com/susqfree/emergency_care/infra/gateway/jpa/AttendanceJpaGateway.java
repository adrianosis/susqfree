package br.com.susqfree.emergency_care.infra.gateway.jpa;

import br.com.susqfree.emergency_care.domain.enums.AttendanceStatus;
import br.com.susqfree.emergency_care.domain.gateway.AttendanceGateway;
import br.com.susqfree.emergency_care.domain.model.Attendance;
import br.com.susqfree.emergency_care.infra.gateway.jpa.mapper.AttendanceEntityMapper;
import br.com.susqfree.emergency_care.infra.gateway.jpa.repository.AttendanceRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AttendanceJpaGateway implements AttendanceGateway {

    private final AttendanceRepository repository;
    private final AttendanceEntityMapper mapper;

    public AttendanceJpaGateway(AttendanceRepository repository, AttendanceEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Attendance save(Attendance attendance) {
        return mapper.toDomain(repository.save(mapper.toEntity(attendance)));
    }

    @Override
    public Optional<Attendance> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Attendance> findByStatusOrderByIdAsc(AttendanceStatus status) {
        return repository.findByStatusOrderByIdAsc(status)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Attendance> findByServiceUnitAndStatus(Long serviceUnitId, AttendanceStatus status) {
        return repository.findByServiceUnitIdAndStatusOrderByIdAsc(serviceUnitId, status)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Attendance> findByServiceUnit(Long serviceUnitId) {
        return repository.findByServiceUnitId(serviceUnitId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByPatientId(UUID patientId) {
        return repository.existsByPatientId(patientId);
    }

    @Override
    public void delete(Attendance attendance) {
        repository.delete(mapper.toEntity(attendance));
    }
}
