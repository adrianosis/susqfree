package br.com.susqfree.emergency_care.domain.service;

import br.com.susqfree.emergency_care.domain.enums.PriorityLevel;
import br.com.susqfree.emergency_care.domain.gateway.AttendanceGateway;
import br.com.susqfree.emergency_care.domain.model.Attendance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TicketGeneratorServiceTest {

    private TicketGeneratorService ticketGeneratorService;

    @Mock
    private AttendanceGateway attendanceGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        ticketGeneratorService = new TicketGeneratorService(attendanceGateway);
    }

    @Test
    void shouldGenerateFirstTicket_WhenNoPreviousTicketsExist() {
        Long serviceUnitId = 1L;

        when(attendanceGateway.findByServiceUnit(serviceUnitId)).thenReturn(List.of());

        String ticket = ticketGeneratorService.generateTicket(PriorityLevel.EMERGENCY, serviceUnitId);

        assertThat(ticket).isEqualTo("R00001");
        verify(attendanceGateway, times(1)).findByServiceUnit(serviceUnitId);
    }

    @Test
    void shouldGenerateNextSequentialTicket_WhenPreviousTicketsExist() {
        Long serviceUnitId = 1L;
        List<Attendance> previousAttendances = List.of(
                new Attendance(1L, null, null, null, "R00001"),
                new Attendance(2L, null, null, null, "R00002")
        );

        when(attendanceGateway.findByServiceUnit(serviceUnitId)).thenReturn(previousAttendances);

        String ticket = ticketGeneratorService.generateTicket(PriorityLevel.EMERGENCY, serviceUnitId);

        assertThat(ticket).isEqualTo("R00003");
        verify(attendanceGateway, times(1)).findByServiceUnit(serviceUnitId);
    }

    @Test
    void shouldGenerateTicketWithCorrectPrefix_ForDifferentPriorities() {
        Long serviceUnitId = 1L;

        when(attendanceGateway.findByServiceUnit(serviceUnitId)).thenReturn(List.of());

        String emergencyTicket = ticketGeneratorService.generateTicket(PriorityLevel.EMERGENCY, serviceUnitId);
        String urgentTicket = ticketGeneratorService.generateTicket(PriorityLevel.URGENT, serviceUnitId);
        String priorityTicket = ticketGeneratorService.generateTicket(PriorityLevel.PRIORITY, serviceUnitId);
        String nonPriorityTicket = ticketGeneratorService.generateTicket(PriorityLevel.NON_PRIORITY, serviceUnitId);

        assertThat(emergencyTicket).isEqualTo("R00001");
        assertThat(urgentTicket).isEqualTo("Y00001");
        assertThat(priorityTicket).isEqualTo("G00001");
        assertThat(nonPriorityTicket).isEqualTo("B00001");

        verify(attendanceGateway, times(4)).findByServiceUnit(serviceUnitId);
    }
}
