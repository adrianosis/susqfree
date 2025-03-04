package br.com.susqfree.emergency_care.infra.gateway.jpa;

import br.com.susqfree.emergency_care.domain.enums.AttendanceStatus;
import br.com.susqfree.emergency_care.domain.model.AttendanceHistory;
import br.com.susqfree.emergency_care.domain.model.ServiceUnit;
import br.com.susqfree.emergency_care.infra.gateway.jpa.entity.AttendanceHistoryEntity;
import br.com.susqfree.emergency_care.infra.gateway.jpa.entity.ServiceUnitEntity;
import br.com.susqfree.emergency_care.infra.gateway.jpa.mapper.AttendanceHistoryEntityMapper;
import br.com.susqfree.emergency_care.infra.gateway.jpa.repository.AttendanceHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AttendanceHistoryJpaGatewayTest {

    private AttendanceHistoryJpaGateway attendanceHistoryJpaGateway;

    @Mock
    private AttendanceHistoryRepository repository;

    @Mock
    private AttendanceHistoryEntityMapper mapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        attendanceHistoryJpaGateway = new AttendanceHistoryJpaGateway(repository, mapper);
    }

    @Test
    @DisplayName("Deve salvar um histórico de atendimento corretamente")
    void shouldSaveAttendanceHistory() {
        UUID patientId = UUID.randomUUID();
        ServiceUnit serviceUnit = new ServiceUnit(1L, "Emergency Unit", 100, 10L);
        AttendanceHistory attendanceHistory = new AttendanceHistory(1L, patientId, serviceUnit, AttendanceStatus.ATTENDANCE_COMPLETED);

        ServiceUnitEntity serviceUnitEntity = new ServiceUnitEntity(1L, "Emergency Unit", 100, 10L);
        AttendanceHistoryEntity entity = new AttendanceHistoryEntity(1L, patientId, serviceUnitEntity, AttendanceStatus.ATTENDANCE_COMPLETED);

        when(mapper.toEntity(attendanceHistory)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(attendanceHistory);

        var savedAttendanceHistory = attendanceHistoryJpaGateway.save(attendanceHistory);

        verify(repository, times(1)).save(entity);
        assertThat(savedAttendanceHistory).isNotNull();
        assertThat(savedAttendanceHistory.getId()).isEqualTo(attendanceHistory.getId());
        assertThat(savedAttendanceHistory.getPatientId()).isEqualTo(attendanceHistory.getPatientId());
        assertThat(savedAttendanceHistory.getServiceUnit().getId()).isEqualTo(attendanceHistory.getServiceUnit().getId());
        assertThat(savedAttendanceHistory.getStatus()).isEqualTo(attendanceHistory.getStatus());
    }
}
