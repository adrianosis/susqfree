package br.com.susqfree.emergency_care.domain.usecase;

import br.com.susqfree.emergency_care.config.exception.PatientAlreadyInQueueException;
import br.com.susqfree.emergency_care.config.exception.PatientNotFoundException;
import br.com.susqfree.emergency_care.config.exception.ServiceUnitNotFoundException;
import br.com.susqfree.emergency_care.domain.enums.AttendanceStatus;
import br.com.susqfree.emergency_care.domain.enums.PriorityLevel;
import br.com.susqfree.emergency_care.domain.gateway.AttendanceGateway;
import br.com.susqfree.emergency_care.domain.gateway.PatientGateway;
import br.com.susqfree.emergency_care.domain.gateway.ServiceUnitGateway;
import br.com.susqfree.emergency_care.domain.model.Attendance;
import br.com.susqfree.emergency_care.domain.model.Patient;
import br.com.susqfree.emergency_care.domain.model.ServiceUnit;
import br.com.susqfree.emergency_care.domain.service.TicketGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CreateAttendanceUseCaseTest {

    private CreateAttendanceUseCase createAttendanceUseCase;

    @Mock
    private AttendanceGateway attendanceGateway;
    @Mock
    private ServiceUnitGateway serviceUnitGateway;
    @Mock
    private TicketGeneratorService ticketGeneratorService;
    @Mock
    private PatientGateway patientGateway;

    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        createAttendanceUseCase = new CreateAttendanceUseCase(
                attendanceGateway, serviceUnitGateway, ticketGeneratorService, patientGateway
        );
    }

    @Test
    void shouldCreateAttendanceSuccessfully() {
        UUID patientId = UUID.randomUUID();
        Long serviceUnitId = 1L;
        PriorityLevel priorityLevel = PriorityLevel.EMERGENCY;

        Patient patient = Patient.builder()
                .id(patientId)
                .name("Carlos Silva")
                .cpf("123.456.789-00")
                .susNumber("9876543210")
                .build();

        ServiceUnit serviceUnit = new ServiceUnit(serviceUnitId, "Emergência", 50, 123L);

        when(patientGateway.findById(patientId)).thenReturn(Optional.of(patient));
        when(serviceUnitGateway.findById(serviceUnitId)).thenReturn(Optional.of(serviceUnit));
        when(attendanceGateway.existsByPatientId(patientId)).thenReturn(false);
        when(ticketGeneratorService.generateTicket(priorityLevel, serviceUnitId)).thenReturn("R00001");

        Attendance newAttendance = new Attendance(null, patientId, serviceUnit, AttendanceStatus.AWAITING_ATTENDANCE, "R00001");
        when(attendanceGateway.save(any(Attendance.class))).thenReturn(newAttendance);

        Attendance createdAttendance = createAttendanceUseCase.execute(patientId, serviceUnitId, priorityLevel);

        verify(patientGateway, times(1)).findById(patientId);
        verify(serviceUnitGateway, times(1)).findById(serviceUnitId);
        verify(attendanceGateway, times(1)).existsByPatientId(patientId);
        verify(ticketGeneratorService, times(1)).generateTicket(priorityLevel, serviceUnitId);
        verify(attendanceGateway, times(1)).save(any(Attendance.class));

        assertThat(createdAttendance).isNotNull();
        assertThat(createdAttendance.getPatientId()).isEqualTo(patientId);
        assertThat(createdAttendance.getServiceUnit()).isEqualTo(serviceUnit);
        assertThat(createdAttendance.getTicket()).isEqualTo("R00001");
        assertThat(createdAttendance.getStatus()).isEqualTo(AttendanceStatus.AWAITING_ATTENDANCE);
    }

    @Test
    void shouldThrowException_WhenPatientNotFound() {
        UUID patientId = UUID.randomUUID();
        Long serviceUnitId = 1L;
        PriorityLevel priorityLevel = PriorityLevel.EMERGENCY;

        when(patientGateway.findById(patientId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> createAttendanceUseCase.execute(patientId, serviceUnitId, priorityLevel))
                .isInstanceOf(PatientNotFoundException.class)
                .hasMessage("Paciente com ID " + patientId + " não encontrado.");

        verify(patientGateway, times(1)).findById(patientId);
        verifyNoMoreInteractions(serviceUnitGateway, attendanceGateway, ticketGeneratorService);
    }

    @Test
    void shouldThrowException_WhenServiceUnitNotFound() {
        UUID patientId = UUID.randomUUID();
        Long serviceUnitId = 1L;
        PriorityLevel priorityLevel = PriorityLevel.EMERGENCY;

        Patient patient = Patient.builder()
                .id(patientId)
                .name("Carlos Silva")
                .cpf("123.456.789-00")
                .susNumber("9876543210")
                .build();

        when(patientGateway.findById(patientId)).thenReturn(Optional.of(patient));
        when(serviceUnitGateway.findById(serviceUnitId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> createAttendanceUseCase.execute(patientId, serviceUnitId, priorityLevel))
                .isInstanceOf(ServiceUnitNotFoundException.class)
                .hasMessage("Service unit not found");

        verify(patientGateway, times(1)).findById(patientId);
        verify(serviceUnitGateway, times(1)).findById(serviceUnitId);
        verifyNoMoreInteractions(attendanceGateway, ticketGeneratorService);
    }

    @Test
    void shouldThrowException_WhenPatientAlreadyInQueue() {
        UUID patientId = UUID.randomUUID();
        Long serviceUnitId = 1L;
        PriorityLevel priorityLevel = PriorityLevel.EMERGENCY;

        Patient patient = Patient.builder()
                .id(patientId)
                .name("Carlos Silva")
                .cpf("123.456.789-00")
                .susNumber("9876543210")
                .build();

        ServiceUnit serviceUnit = new ServiceUnit(serviceUnitId, "Emergência", 50, 123L);

        when(patientGateway.findById(patientId)).thenReturn(Optional.of(patient));
        when(serviceUnitGateway.findById(serviceUnitId)).thenReturn(Optional.of(serviceUnit));
        when(attendanceGateway.existsByPatientId(patientId)).thenReturn(true);

        assertThatThrownBy(() -> createAttendanceUseCase.execute(patientId, serviceUnitId, priorityLevel))
                .isInstanceOf(PatientAlreadyInQueueException.class);

        verify(patientGateway, times(1)).findById(patientId);
        verify(serviceUnitGateway, times(1)).findById(serviceUnitId);
        verify(attendanceGateway, times(1)).existsByPatientId(patientId);
        verifyNoMoreInteractions(ticketGeneratorService, attendanceGateway);
    }
}
