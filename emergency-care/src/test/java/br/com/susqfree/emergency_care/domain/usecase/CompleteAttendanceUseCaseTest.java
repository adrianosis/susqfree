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

class CompleteAttendanceUseCaseTest {

    private CompleteAttendanceUseCase completeAttendanceUseCase;

    @Mock
    private AttendanceGateway attendanceGateway;

    @Mock
    private AttendanceHistoryGateway attendanceHistoryGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        completeAttendanceUseCase = new CompleteAttendanceUseCase(attendanceGateway, attendanceHistoryGateway);
    }

    @Test
    void shouldCompleteAttendanceAndSaveToHistory() {
        Long attendanceId = 1L;
        UUID patientId = UUID.randomUUID();
        Attendance attendance = new Attendance(attendanceId, patientId, null, AttendanceStatus.IN_ATTENDANCE, "R00002");

        when(attendanceGateway.findById(attendanceId)).thenReturn(Optional.of(attendance));
        when(attendanceGateway.save(any(Attendance.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(attendanceHistoryGateway.save(any(AttendanceHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        completeAttendanceUseCase.execute(attendanceId);

        verify(attendanceGateway, times(1)).findById(attendanceId);
        verify(attendanceGateway, times(1)).save(any(Attendance.class));
        verify(attendanceHistoryGateway, times(1)).save(any(AttendanceHistory.class));
        verify(attendanceGateway, times(1)).delete(any(Attendance.class));
    }

    @Test
    void shouldThrowExceptionWhenAttendanceNotFound() {
        Long attendanceId = 1L;
        when(attendanceGateway.findById(attendanceId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> completeAttendanceUseCase.execute(attendanceId))
                .isInstanceOf(AttendanceNotFoundException.class)
                .hasMessage("Attendance not found");

        verify(attendanceGateway, times(1)).findById(attendanceId);
        verify(attendanceGateway, never()).save(any(Attendance.class));
        verify(attendanceHistoryGateway, never()).save(any(AttendanceHistory.class));
        verify(attendanceGateway, never()).delete(any(Attendance.class));
    }
}
