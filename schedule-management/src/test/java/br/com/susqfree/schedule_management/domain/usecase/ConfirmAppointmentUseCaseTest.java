package br.com.susqfree.schedule_management.domain.usecase;

import br.com.susqfree.schedule_management.domain.gateway.AppointmentGateway;
import br.com.susqfree.schedule_management.domain.input.ConfirmAppointmentInput;
import br.com.susqfree.schedule_management.domain.mapper.AppointmentOutputMapper;
import br.com.susqfree.schedule_management.domain.model.Appointment;
import br.com.susqfree.schedule_management.domain.model.Status;
import br.com.susqfree.schedule_management.infra.exception.AppointmentException;
import br.com.susqfree.schedule_management.utils.AppointmentHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ConfirmAppointmentUseCaseTest {

    private ConfirmAppointmentUseCase confirmAppointmentUseCase;

    @Mock
    private AppointmentGateway appointmentGateway;
    AutoCloseable openMocks;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        confirmAppointmentUseCase = new ConfirmAppointmentUseCase(appointmentGateway, new AppointmentOutputMapper());
    }

    @Test
    public void shouldConfirmAppointment() {
        // Arrange
        UUID appointmentId = UUID.randomUUID();
        Appointment appointment = AppointmentHelper.createAppointment(appointmentId, Status.SCHEDULED);

        when(appointmentGateway.findById(any(UUID.class))).thenReturn(Optional.ofNullable(appointment));
        when(appointmentGateway.save(any(Appointment.class))).thenAnswer(returnsFirstArg());

        var input = ConfirmAppointmentInput.builder()
                .appointmentId(appointmentId)
                .confirmed(true)
                .build();

        // Act
        var updatedAppointment = confirmAppointmentUseCase.execute(input);

        // Assert
        verify(appointmentGateway, times(1)).findById(any(UUID.class));
        verify(appointmentGateway, times(1)).save(any(Appointment.class));

        assertThat(updatedAppointment.getStatus()).isEqualTo(Status.CONFIRMED);
    }

    @Test
    public void shouldConfirmAppointmentWithNotResponse() {
        // Arrange
        UUID appointmentId = UUID.randomUUID();
        Appointment appointment = AppointmentHelper.createAppointment(appointmentId, Status.SCHEDULED);

        when(appointmentGateway.findById(any(UUID.class))).thenReturn(Optional.ofNullable(appointment));
        when(appointmentGateway.save(any(Appointment.class))).thenAnswer(returnsFirstArg());

        var input = ConfirmAppointmentInput.builder()
                .appointmentId(appointmentId)
                .confirmed(false)
                .build();

        // Act
        var updatedAppointment = confirmAppointmentUseCase.execute(input);

        // Assert
        verify(appointmentGateway, times(1)).findById(any(UUID.class));
        verify(appointmentGateway, times(1)).save(any(Appointment.class));

        assertThat(updatedAppointment.getStatus()).isEqualTo(Status.AVAILABLE);
    }

    @Test
    public void shouldThrowException_WhenConfirmAppointment_WithStatusAvailable() {
        UUID appointmentId = UUID.randomUUID();
        Appointment appointment = AppointmentHelper.createAppointment(appointmentId, Status.AVAILABLE);

        when(appointmentGateway.findById(any(UUID.class))).thenReturn(Optional.ofNullable(appointment));
        when(appointmentGateway.save(any(Appointment.class))).thenAnswer(returnsFirstArg());

        var input = ConfirmAppointmentInput.builder()
                .appointmentId(appointmentId)
                .confirmed(false)
                .build();


        // Act
        // Assert
        assertThatThrownBy(
                () -> confirmAppointmentUseCase.execute(input))
                .isInstanceOf(AppointmentException.class)
                .hasMessage("Appointment not scheduled");

        verify(appointmentGateway, times(1)).findById(any(UUID.class));
    }

}