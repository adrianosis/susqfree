package br.com.susqfree.schedule_management.domain.usecase;

import br.com.susqfree.schedule_management.domain.gateway.AppointmentGateway;
import br.com.susqfree.schedule_management.domain.gateway.PatientGateway;
import br.com.susqfree.schedule_management.domain.input.ScheduleAppointmentInput;
import br.com.susqfree.schedule_management.domain.mapper.AppointmentOutputMapper;
import br.com.susqfree.schedule_management.domain.model.Appointment;
import br.com.susqfree.schedule_management.domain.model.Patient;
import br.com.susqfree.schedule_management.domain.model.Status;
import br.com.susqfree.schedule_management.infra.exception.AppointmentException;
import br.com.susqfree.schedule_management.utils.PatientHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static br.com.susqfree.schedule_management.utils.DoctorHelper.createDoctor;
import static br.com.susqfree.schedule_management.utils.HealthUnitHelper.createHealthUnit;
import static br.com.susqfree.schedule_management.utils.SpecialtyHelper.createSpecialty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ScheduleAppointmentUseCaseTest {

    private ScheduleAppointmentUseCase scheduleAppointmentUseCase;

    @Mock
    private AppointmentGateway appointmentGateway;
    @Mock
    private PatientGateway patientGateway;
    AutoCloseable openMocks;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        scheduleAppointmentUseCase = new ScheduleAppointmentUseCase(appointmentGateway, patientGateway, new AppointmentOutputMapper());
    }

    @Test
    public void shouldScheduleAppointment() {
        // Arrange
        UUID appointmentId = UUID.randomUUID();
        Appointment appointment = Appointment.builder()
                .id(appointmentId)
                .dateTime(LocalDateTime.of(2025, 3, 1, 8, 0))
                .status(Status.AVAILABLE)
                .doctor(createDoctor())
                .healthUnit(createHealthUnit())
                .specialty(createSpecialty())
                .build();
        Patient patient = PatientHelper.createPatient();

        when(appointmentGateway.findById(any(UUID.class))).thenReturn(Optional.ofNullable(appointment));
        when(appointmentGateway.save(any(Appointment.class))).thenAnswer(returnsFirstArg());
        when(patientGateway.findById(any(UUID.class))).thenReturn(Optional.ofNullable(patient));

        var input = ScheduleAppointmentInput.builder()
                .appointmentId(appointmentId)
                .patientId(UUID.randomUUID())
                .build();

        // Act
        var canceledAppointment = scheduleAppointmentUseCase.execute(input);

        // Assert
        verify(appointmentGateway, times(1)).findById(any(UUID.class));
        verify(patientGateway, times(1)).findById(any(UUID.class));
        verify(appointmentGateway, times(1)).save(any(Appointment.class));

        assertThat(canceledAppointment.getStatus()).isEqualTo(Status.SCHEDULED);
        assertThat(canceledAppointment.getPatient()).isNotNull();
    }

    @Test
    public void shouldThrowException_WhenScheduleAppointment_WithStatusNotAvailable() {
        // Arrange
        UUID appointmentId = UUID.randomUUID();
        Appointment appointment = Appointment.builder()
                .id(appointmentId)
                .dateTime(LocalDateTime.of(2025, 3, 1, 8, 0))
                .status(Status.CANCELLED)
                .doctor(createDoctor())
                .healthUnit(createHealthUnit())
                .specialty(createSpecialty())
                .build();
        Patient patient = PatientHelper.createPatient();

        when(appointmentGateway.findById(any(UUID.class))).thenReturn(Optional.ofNullable(appointment));
        when(patientGateway.findById(any(UUID.class))).thenReturn(Optional.ofNullable(patient));

        var input = ScheduleAppointmentInput.builder()
                .appointmentId(appointmentId)
                .patientId(UUID.randomUUID())
                .build();

        // Act
        // Assert
        assertThatThrownBy(
                () -> scheduleAppointmentUseCase.execute(input))
                .isInstanceOf(AppointmentException.class)
                .hasMessage("Schedule not available");


        verify(appointmentGateway, times(1)).findById(any(UUID.class));
        verify(patientGateway, times(1)).findById(any(UUID.class));
    }

}