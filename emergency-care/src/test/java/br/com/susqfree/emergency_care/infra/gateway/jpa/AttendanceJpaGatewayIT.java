package br.com.susqfree.emergency_care.infra.gateway.jpa;

import br.com.susqfree.emergency_care.domain.enums.AttendanceStatus;
import br.com.susqfree.emergency_care.domain.model.Attendance;
import br.com.susqfree.emergency_care.infra.gateway.jpa.entity.AttendanceEntity;
import br.com.susqfree.emergency_care.infra.gateway.jpa.entity.ServiceUnitEntity;
import br.com.susqfree.emergency_care.infra.gateway.jpa.mapper.AttendanceEntityMapper;
import br.com.susqfree.emergency_care.infra.gateway.jpa.mapper.ServiceUnitEntityMapper;
import br.com.susqfree.emergency_care.infra.gateway.jpa.repository.AttendanceRepository;
import br.com.susqfree.emergency_care.infra.gateway.jpa.repository.ServiceUnitRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import({AttendanceEntityMapper.class, ServiceUnitEntityMapper.class})
class AttendanceJpaGatewayIT {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ServiceUnitRepository serviceUnitRepository;

    @Autowired
    private AttendanceEntityMapper attendanceEntityMapper;

    private AttendanceJpaGateway attendanceJpaGateway;

    private ServiceUnitEntity serviceUnitEntity;

    @BeforeEach
    void setUp() {
        attendanceJpaGateway = new AttendanceJpaGateway(attendanceRepository, attendanceEntityMapper);

        serviceUnitEntity = serviceUnitRepository.save(new ServiceUnitEntity(null, "Emergency Unit", 100, 10L));
        serviceUnitRepository.flush();
    }

    @Test
    void shouldSaveAttendanceSuccessfully() {
        Attendance attendance = new Attendance(
                null,
                UUID.randomUUID(),
                new br.com.susqfree.emergency_care.domain.model.ServiceUnit(serviceUnitEntity.getId(), "Emergency Unit", 100, 10L),
                AttendanceStatus.AWAITING_ATTENDANCE,
                "R00001"
        );

        Attendance savedAttendance = attendanceJpaGateway.save(attendance);

        assertThat(savedAttendance).isNotNull();
        assertThat(savedAttendance.getId()).isNotNull();
        assertThat(savedAttendance.getPatientId()).isEqualTo(attendance.getPatientId());
        assertThat(savedAttendance.getServiceUnit().getId()).isEqualTo(attendance.getServiceUnit().getId());
        assertThat(savedAttendance.getStatus()).isEqualTo(attendance.getStatus());
        assertThat(savedAttendance.getTicket()).isEqualTo(attendance.getTicket());
    }

    @Test
    @Transactional
    void shouldFindAttendanceByIdSuccessfully() {
        AttendanceEntity entity = attendanceRepository.save(new AttendanceEntity(
                null, UUID.randomUUID(), serviceUnitEntity, AttendanceStatus.AWAITING_ATTENDANCE, "R00001"
        ));
        attendanceRepository.flush();

        Optional<Attendance> foundAttendance = attendanceJpaGateway.findById(entity.getId());

        assertThat(foundAttendance).isPresent();
        assertThat(foundAttendance.get().getId()).isEqualTo(entity.getId());
    }

    @Test
    void shouldFindAttendancesByStatusSuccessfully() {
        attendanceRepository.saveAll(List.of(
                new AttendanceEntity(null, UUID.randomUUID(), serviceUnitEntity, AttendanceStatus.AWAITING_ATTENDANCE, "R00002"),
                new AttendanceEntity(null, UUID.randomUUID(), serviceUnitEntity, AttendanceStatus.IN_ATTENDANCE, "R00003")
        ));

        List<Attendance> attendances = attendanceJpaGateway.findByStatusOrderByIdAsc(AttendanceStatus.AWAITING_ATTENDANCE);

        assertThat(attendances).hasSize(1);
        assertThat(attendances.get(0).getStatus()).isEqualTo(AttendanceStatus.AWAITING_ATTENDANCE);
    }

    @Test
    void shouldFindAttendancesByServiceUnitAndStatusSuccessfully() {
        ServiceUnitEntity anotherServiceUnit = serviceUnitRepository.save(new ServiceUnitEntity(null, "ICU", 50, 5L));

        attendanceRepository.saveAll(List.of(
                new AttendanceEntity(null, UUID.randomUUID(), serviceUnitEntity, AttendanceStatus.AWAITING_ATTENDANCE, "R00004"),
                new AttendanceEntity(null, UUID.randomUUID(), serviceUnitEntity, AttendanceStatus.AWAITING_ATTENDANCE, "R00005"),
                new AttendanceEntity(null, UUID.randomUUID(), anotherServiceUnit, AttendanceStatus.AWAITING_ATTENDANCE, "R00006")
        ));

        List<Attendance> attendances = attendanceJpaGateway.findByServiceUnitAndStatus(serviceUnitEntity.getId(), AttendanceStatus.AWAITING_ATTENDANCE);

        assertThat(attendances).hasSize(2);
    }

    @Test
    void shouldCheckIfAttendanceExistsByPatientId() {
        UUID patientId = UUID.randomUUID();
        attendanceRepository.save(new AttendanceEntity(null, patientId, serviceUnitEntity, AttendanceStatus.AWAITING_ATTENDANCE, "R00007"));

        boolean exists = attendanceJpaGateway.existsByPatientId(patientId);

        assertThat(exists).isTrue();
    }

    @Test
    void shouldDeleteAttendanceSuccessfully() {
        AttendanceEntity entity = attendanceRepository.save(new AttendanceEntity(
                null, UUID.randomUUID(), serviceUnitEntity, AttendanceStatus.AWAITING_ATTENDANCE, "R00008"
        ));
        attendanceRepository.flush();

        Attendance attendance = attendanceJpaGateway.findById(entity.getId()).orElseThrow();
        attendanceJpaGateway.delete(attendance);

        Optional<Attendance> deletedAttendance = attendanceJpaGateway.findById(entity.getId());
        assertThat(deletedAttendance).isEmpty();
    }

    @Test
    void shouldUpdateAttendanceStatusSuccessfully() {
        AttendanceEntity entity = attendanceRepository.save(new AttendanceEntity(
                null, UUID.randomUUID(), serviceUnitEntity, AttendanceStatus.AWAITING_ATTENDANCE, "R00009"
        ));
        attendanceRepository.flush();

        Attendance attendance = attendanceJpaGateway.findById(entity.getId()).orElseThrow();
        attendance = new Attendance(attendance.getId(), attendance.getPatientId(), attendance.getServiceUnit(), AttendanceStatus.IN_ATTENDANCE, attendance.getTicket());

        Attendance updatedAttendance = attendanceJpaGateway.save(attendance);

        assertThat(updatedAttendance.getStatus()).isEqualTo(AttendanceStatus.IN_ATTENDANCE);
    }

}
