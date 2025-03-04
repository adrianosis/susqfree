package br.com.susqfree.emergency_care.infra.gateway.jpa;

import br.com.susqfree.emergency_care.domain.enums.AttendanceStatus;
import br.com.susqfree.emergency_care.domain.model.Attendance;
import br.com.susqfree.emergency_care.infra.gateway.jpa.entity.AttendanceEntity;
import br.com.susqfree.emergency_care.infra.gateway.jpa.mapper.AttendanceEntityMapper;
import br.com.susqfree.emergency_care.infra.gateway.jpa.repository.AttendanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AttendanceJpaGatewayTest {

    private AttendanceJpaGateway attendanceJpaGateway;

    @Mock
    private AttendanceRepository repository;

    @Mock
    private AttendanceEntityMapper mapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        attendanceJpaGateway = new AttendanceJpaGateway(repository, mapper);
    }

    @Test
    @DisplayName("Deve salvar um atendimento")
    void shouldSaveAttendance() {
        var attendance = new Attendance(1L, UUID.randomUUID(), null, AttendanceStatus.AWAITING_ATTENDANCE, "R00001");
        var entity = new AttendanceEntity(1L, attendance.getPatientId(), null, attendance.getStatus(), attendance.getTicket());

        when(mapper.toEntity(any(Attendance.class))).thenReturn(entity);
        when(repository.save(any(AttendanceEntity.class))).thenReturn(entity);
        when(mapper.toDomain(any(AttendanceEntity.class))).thenReturn(attendance);

        var savedAttendance = attendanceJpaGateway.save(attendance);

        verify(repository, times(1)).save(any(AttendanceEntity.class));
        assertThat(savedAttendance).isNotNull();
        assertThat(savedAttendance.getId()).isEqualTo(attendance.getId());
    }

    @Test
    @DisplayName("Deve encontrar um atendimento por ID")
    void shouldFindAttendanceById() {
        var attendance = new Attendance(1L, UUID.randomUUID(), null, AttendanceStatus.AWAITING_ATTENDANCE, "R00001");
        var entity = new AttendanceEntity(1L, attendance.getPatientId(), null, attendance.getStatus(), attendance.getTicket());

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(attendance);

        var foundAttendance = attendanceJpaGateway.findById(1L);

        verify(repository, times(1)).findById(1L);
        assertThat(foundAttendance).isPresent();
        assertThat(foundAttendance.get().getId()).isEqualTo(attendance.getId());
    }

    @Test
    @DisplayName("Deve retornar vazio quando não encontrar atendimento por ID")
    void shouldReturnEmptyWhenAttendanceNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        var foundAttendance = attendanceJpaGateway.findById(99L);

        verify(repository, times(1)).findById(99L);
        assertThat(foundAttendance).isEmpty();
    }

    @Test
    @DisplayName("Deve listar atendimentos pelo status")
    void shouldFindAttendancesByStatus() {
        var attendance = new Attendance(1L, UUID.randomUUID(), null, AttendanceStatus.AWAITING_ATTENDANCE, "R00001");
        var entity = new AttendanceEntity(1L, attendance.getPatientId(), null, attendance.getStatus(), attendance.getTicket());

        when(repository.findByStatusOrderByIdAsc(AttendanceStatus.AWAITING_ATTENDANCE)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(attendance);

        var attendances = attendanceJpaGateway.findByStatusOrderByIdAsc(AttendanceStatus.AWAITING_ATTENDANCE);

        verify(repository, times(1)).findByStatusOrderByIdAsc(AttendanceStatus.AWAITING_ATTENDANCE);
        assertThat(attendances).hasSize(1);
    }

    @Test
    @DisplayName("Deve listar atendimentos por unidade de serviço e status")
    void shouldFindAttendancesByServiceUnitAndStatus() {
        var attendance = new Attendance(1L, UUID.randomUUID(), null, AttendanceStatus.AWAITING_ATTENDANCE, "R00001");
        var entity = new AttendanceEntity(1L, attendance.getPatientId(), null, attendance.getStatus(), attendance.getTicket());

        when(repository.findByServiceUnitIdAndStatusOrderByIdAsc(2L, AttendanceStatus.AWAITING_ATTENDANCE)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(attendance);

        var attendances = attendanceJpaGateway.findByServiceUnitAndStatus(2L, AttendanceStatus.AWAITING_ATTENDANCE);

        verify(repository, times(1)).findByServiceUnitIdAndStatusOrderByIdAsc(2L, AttendanceStatus.AWAITING_ATTENDANCE);
        assertThat(attendances).hasSize(1);
    }

    @Test
    @DisplayName("Deve listar atendimentos por unidade de serviço")
    void shouldFindAttendancesByServiceUnit() {
        var attendance = new Attendance(1L, UUID.randomUUID(), null, AttendanceStatus.AWAITING_ATTENDANCE, "R00001");
        var entity = new AttendanceEntity(1L, attendance.getPatientId(), null, attendance.getStatus(), attendance.getTicket());

        when(repository.findByServiceUnitId(2L)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(attendance);

        var attendances = attendanceJpaGateway.findByServiceUnit(2L);

        verify(repository, times(1)).findByServiceUnitId(2L);
        assertThat(attendances).hasSize(1);
    }

    @Test
    @DisplayName("Deve verificar se um paciente já possui atendimento")
    void shouldCheckIfPatientHasAttendance() {
        var patientId = UUID.randomUUID();

        when(repository.existsByPatientId(patientId)).thenReturn(true);

        var exists = attendanceJpaGateway.existsByPatientId(patientId);

        verify(repository, times(1)).existsByPatientId(patientId);
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve excluir um atendimento")
    void shouldDeleteAttendance() {
        var attendance = new Attendance(1L, UUID.randomUUID(), null, AttendanceStatus.AWAITING_ATTENDANCE, "R00001");
        var entity = new AttendanceEntity(1L, attendance.getPatientId(), null, attendance.getStatus(), attendance.getTicket());

        when(mapper.toEntity(attendance)).thenReturn(entity);
        doNothing().when(repository).delete(entity);

        attendanceJpaGateway.delete(attendance);

        verify(repository, times(1)).delete(entity);
    }
}
