package br.com.susqfree.emergency_care.infra.gateway.jpa;

import br.com.susqfree.emergency_care.domain.enums.AttendanceStatus;
import br.com.susqfree.emergency_care.domain.model.AttendanceHistory;
import br.com.susqfree.emergency_care.domain.model.ServiceUnit;
import br.com.susqfree.emergency_care.infra.gateway.jpa.entity.ServiceUnitEntity;
import br.com.susqfree.emergency_care.infra.gateway.jpa.mapper.AttendanceHistoryEntityMapper;
import br.com.susqfree.emergency_care.infra.gateway.jpa.mapper.ServiceUnitEntityMapper;
import br.com.susqfree.emergency_care.infra.gateway.jpa.repository.AttendanceHistoryRepository;
import br.com.susqfree.emergency_care.infra.gateway.jpa.repository.ServiceUnitRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import({AttendanceHistoryEntityMapper.class, ServiceUnitEntityMapper.class})
class AttendanceHistoryJpaGatewayIT {

    @Autowired
    private AttendanceHistoryRepository repository;

    @Autowired
    private ServiceUnitRepository serviceUnitRepository;

    @Autowired
    private AttendanceHistoryEntityMapper mapper;

    private AttendanceHistoryJpaGateway gateway;

    private ServiceUnitEntity serviceUnitEntity;

    @BeforeEach
    void setUp() {
        gateway = new AttendanceHistoryJpaGateway(repository, mapper);

        serviceUnitEntity = serviceUnitRepository.save(new ServiceUnitEntity(null, "Emergency Unit", 100, 10L));
        serviceUnitRepository.flush();
    }

    @Test
    @Transactional
    void shouldSaveAttendanceHistorySuccessfully() {
        AttendanceHistory attendanceHistory = new AttendanceHistory(
                null,
                UUID.randomUUID(),
                new ServiceUnit(serviceUnitEntity.getId(), "Emergency Unit", 100, 10L),
                AttendanceStatus.ATTENDANCE_COMPLETED
        );

        AttendanceHistory savedHistory = gateway.save(attendanceHistory);

        assertThat(savedHistory).isNotNull();
        assertThat(savedHistory.getId()).isNotNull();
        assertThat(savedHistory.getPatientId()).isEqualTo(attendanceHistory.getPatientId());
        assertThat(savedHistory.getServiceUnit().getId()).isEqualTo(attendanceHistory.getServiceUnit().getId());
        assertThat(savedHistory.getStatus()).isEqualTo(attendanceHistory.getStatus());
    }
}
