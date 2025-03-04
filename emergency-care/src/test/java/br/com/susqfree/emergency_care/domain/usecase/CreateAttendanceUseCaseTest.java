package br.com.susqfree.emergency_care.domain.usecase;

import br.com.susqfree.emergency_care.config.exception.PatientAlreadyInQueueException;
import br.com.susqfree.emergency_care.config.exception.PatientNotFoundException;
import br.com.susqfree.emergency_care.config.exception.ServiceUnitNotFoundException;
import br.com.susqfree.emergency_care.domain.enums.AttendanceStatus;
import br.com.susqfree.emergency_care.domain.enums.PriorityLevel;
import br.com.susqfree.emergency_care.domain.gateway.AttendanceGateway;
import br.com.susqfree.emergency_care.domain.gateway.PatientGateway;
import br.com.susqfree.emergency_care.domain.gateway.ServiceUnitGateway;
import br.com.susqfree.emergency_care.domain.gateway.TriageGateway;
import br.com.susqfree.emergency_care.domain.model.*;
import br.com.susqfree.emergency_care.domain.service.TicketGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
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
    @Mock
    private TriageGateway triageGateway;

    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        createAttendanceUseCase = new CreateAttendanceUseCase(
                attendanceGateway, serviceUnitGateway, ticketGeneratorService, patientGateway, triageGateway
        );
    }

    @Test
    void shouldCreateAttendanceSuccessfully() {
        UUID patientId = UUID.randomUUID();
        Long serviceUnitId = 1L;
        TriageInput triageInput = createTriageInput(patientId);

        Patient patient = Patient.builder()
                .id(patientId)
                .name("Carlos Silva")
                .cpf("123.456.789-00")
                .susNumber("9876543210")
                .build();

        ServiceUnit serviceUnit = new ServiceUnit(serviceUnitId, "Emergência", 50, 123L);

        TriagePriorityOutput triagePriorityDto = new TriagePriorityOutput("R", "Emergência - Atendimento imediato necessário.");
        when(triageGateway.processTriage(triageInput)).thenReturn(triagePriorityDto);

        when(patientGateway.findById(patientId)).thenReturn(Optional.of(patient));
        when(serviceUnitGateway.findById(serviceUnitId)).thenReturn(Optional.of(serviceUnit));
        when(attendanceGateway.existsByPatientId(patientId)).thenReturn(false);
        when(ticketGeneratorService.generateTicket(PriorityLevel.EMERGENCY, serviceUnitId)).thenReturn("R00001");

        Attendance newAttendance = new Attendance(null, patientId, serviceUnit, AttendanceStatus.AWAITING_ATTENDANCE, "R00001");
        when(attendanceGateway.save(any(Attendance.class))).thenReturn(newAttendance);

        Attendance createdAttendance = createAttendanceUseCase.execute(patientId, serviceUnitId, triageInput);

        verify(triageGateway, times(1)).processTriage(triageInput);
        verify(patientGateway, times(1)).findById(patientId);
        verify(serviceUnitGateway, times(1)).findById(serviceUnitId);
        verify(attendanceGateway, times(1)).existsByPatientId(patientId);
        verify(ticketGeneratorService, times(1)).generateTicket(PriorityLevel.EMERGENCY, serviceUnitId);
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
        TriageInput triageInput = createTriageInput(patientId);

        when(patientGateway.findById(patientId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> createAttendanceUseCase.execute(patientId, serviceUnitId, triageInput))
                .isInstanceOf(PatientNotFoundException.class)
                .hasMessage("Paciente com ID " + patientId + " não encontrado.");

        verify(patientGateway, times(1)).findById(patientId);
        verifyNoMoreInteractions(serviceUnitGateway, attendanceGateway, triageGateway, ticketGeneratorService);
    }

    @Test
    void shouldThrowException_WhenServiceUnitNotFound() {
        UUID patientId = UUID.randomUUID();
        Long serviceUnitId = 1L;
        TriageInput triageInput = createTriageInput(patientId);

        Patient patient = Patient.builder()
                .id(patientId)
                .name("Carlos Silva")
                .cpf("123.456.789-00")
                .susNumber("9876543210")
                .build();

        when(patientGateway.findById(patientId)).thenReturn(Optional.of(patient));
        when(serviceUnitGateway.findById(serviceUnitId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> createAttendanceUseCase.execute(patientId, serviceUnitId, triageInput))
                .isInstanceOf(ServiceUnitNotFoundException.class)
                .hasMessage("Service unit not found");

        verify(patientGateway, times(1)).findById(patientId);
        verify(serviceUnitGateway, times(1)).findById(serviceUnitId);
        verifyNoMoreInteractions(attendanceGateway, triageGateway, ticketGeneratorService);
    }

    @Test
    void shouldThrowException_WhenPatientAlreadyInQueue() {
        UUID patientId = UUID.randomUUID();
        Long serviceUnitId = 1L;
        TriageInput triageInput = createTriageInput(patientId);

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

        assertThatThrownBy(() -> createAttendanceUseCase.execute(patientId, serviceUnitId, triageInput))
                .isInstanceOf(PatientAlreadyInQueueException.class);

        verify(patientGateway, times(1)).findById(patientId);
        verify(serviceUnitGateway, times(1)).findById(serviceUnitId);
        verify(attendanceGateway, times(1)).existsByPatientId(patientId);
        verifyNoMoreInteractions(triageGateway, ticketGeneratorService, attendanceGateway);
    }

    private TriageInput createTriageInput(UUID patientId) {
        return new TriageInput(
                patientId,
                "Dor intensa no peito",
                "MENOS_DE_UM_DIA",
                39.5,
                "NORMAL",
                "TAQUICARDIA",
                "NORMAL",
                "NORMAL",
                List.of("DOR_PEITO", "FALTA_AR"),
                "INTENSA",
                "NAO",
                List.of("HIPERTENSAO"),
                List.of(),
                "NAO",
                List.of()
        );
    }
}

