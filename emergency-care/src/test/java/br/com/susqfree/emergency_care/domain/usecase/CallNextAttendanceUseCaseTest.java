package br.com.susqfree.emergency_care.domain.usecase;

import br.com.susqfree.emergency_care.domain.enums.AttendanceStatus;
import br.com.susqfree.emergency_care.domain.gateway.AttendanceGateway;
import br.com.susqfree.emergency_care.domain.model.Attendance;
import br.com.susqfree.emergency_care.domain.model.ServiceUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CallNextAttendanceUseCaseTest {

    private CallNextAttendanceUseCase callNextAttendanceUseCase;

    @Mock
    private AttendanceGateway attendanceGateway;

    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        callNextAttendanceUseCase = new CallNextAttendanceUseCase(attendanceGateway);
    }

    @Test
    void shouldCallNextAttendanceByPriority() {
        Long serviceUnitId = 1L;
        ServiceUnit serviceUnit = new ServiceUnit(serviceUnitId, "Emergência", 50, 123L);

        Attendance attendance1 = new Attendance(1L, UUID.randomUUID(), serviceUnit, AttendanceStatus.AWAITING_ATTENDANCE, "R00001");
        Attendance attendance2 = new Attendance(2L, UUID.randomUUID(), serviceUnit, AttendanceStatus.AWAITING_ATTENDANCE, "Y00002");
        Attendance attendance3 = new Attendance(3L, UUID.randomUUID(), serviceUnit, AttendanceStatus.AWAITING_ATTENDANCE, "G00003");

        List<Attendance> queue = List.of(attendance1, attendance2, attendance3);

        when(attendanceGateway.findByServiceUnitAndStatus(serviceUnitId, AttendanceStatus.AWAITING_ATTENDANCE))
                .thenReturn(queue);
        when(attendanceGateway.save(any(Attendance.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Attendance> nextAttendance = callNextAttendanceUseCase.execute(serviceUnitId);

        assertThat(nextAttendance).isPresent();
        assertThat(nextAttendance.get().getId()).isEqualTo(attendance1.getId());
        assertThat(nextAttendance.get().getStatus()).isEqualTo(AttendanceStatus.IN_ATTENDANCE);
        assertThat(nextAttendance.get().getTicket()).isEqualTo("R00001");

        verify(attendanceGateway, times(1)).findByServiceUnitAndStatus(serviceUnitId, AttendanceStatus.AWAITING_ATTENDANCE);
        verify(attendanceGateway, times(1)).save(any(Attendance.class));
    }

    @Test
    void shouldReturnEmptyWhenNoPatientsInQueue() {
        Long serviceUnitId = 1L;
        when(attendanceGateway.findByServiceUnitAndStatus(serviceUnitId, AttendanceStatus.AWAITING_ATTENDANCE))
                .thenReturn(List.of());

        Optional<Attendance> nextAttendance = callNextAttendanceUseCase.execute(serviceUnitId);

        assertThat(nextAttendance).isEmpty();
        verify(attendanceGateway, atLeastOnce()).findByServiceUnitAndStatus(serviceUnitId, AttendanceStatus.AWAITING_ATTENDANCE);
        verify(attendanceGateway, never()).save(any(Attendance.class));
    }
}
