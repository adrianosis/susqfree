package br.com.susqfree.emergency_care.domain.usecase;

import br.com.susqfree.emergency_care.config.exception.AttendanceNotFoundException;
import br.com.susqfree.emergency_care.domain.enums.AttendanceStatus;
import br.com.susqfree.emergency_care.domain.gateway.AttendanceGateway;
import br.com.susqfree.emergency_care.domain.gateway.AttendanceHistoryGateway;
import br.com.susqfree.emergency_care.domain.model.Attendance;
import br.com.susqfree.emergency_care.domain.model.AttendanceHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CancelAttendanceUseCaseTest {

    private CancelAttendanceUseCase cancelAttendanceUseCase;

    @Mock
    private AttendanceGateway attendanceGateway;

    @Mock
    private AttendanceHistoryGateway attendanceHistoryGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        cancelAttendanceUseCase = new CancelAttendanceUseCase(attendanceGateway, attendanceHistoryGateway);
    }

    @Test
    void shouldCancelAttendanceAndSaveToHistory() {
        Long attendanceId = 1L;
        UUID patientId = UUID.randomUUID();
        Attendance attendance = new Attendance(attendanceId, patientId, null, AttendanceStatus.AWAITING_ATTENDANCE, "R00001");

        when(attendanceGateway.findById(attendanceId)).thenReturn(Optional.of(attendance));
        when(attendanceGateway.save(any(Attendance.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(attendanceHistoryGateway.save(any(AttendanceHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        cancelAttendanceUseCase.execute(attendanceId);

        verify(attendanceGateway, times(1)).findById(attendanceId);
        verify(attendanceGateway, times(1)).save(any(Attendance.class));
        verify(attendanceHistoryGateway, times(1)).save(any(AttendanceHistory.class));
        verify(attendanceGateway, times(1)).delete(any(Attendance.class));
    }

    @Test
    void shouldThrowExceptionWhenAttendanceNotFound() {
        Long attendanceId = 1L;
        when(attendanceGateway.findById(attendanceId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cancelAttendanceUseCase.execute(attendanceId))
                .isInstanceOf(AttendanceNotFoundException.class)
                .hasMessage("Attendance not found");

        verify(attendanceGateway, times(1)).findById(attendanceId);
        verify(attendanceGateway, never()).save(any(Attendance.class));
        verify(attendanceHistoryGateway, never()).save(any(AttendanceHistory.class));
        verify(attendanceGateway, never()).delete(any(Attendance.class));
    }
}
