package br.com.susqfree.schedule_management.domain.usecase;

import br.com.susqfree.schedule_management.domain.gateway.AppointmentGateway;
import br.com.susqfree.schedule_management.domain.mapper.AppointmentOutputMapper;
import br.com.susqfree.schedule_management.domain.model.Appointment;
import br.com.susqfree.schedule_management.domain.model.Status;
import br.com.susqfree.schedule_management.utils.AppointmentHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CompleteAppointmentUseCaseTest {

    private CompleteAppointmentUseCase completeAppointmentUseCase;

    @Mock
    private AppointmentGateway appointmentGateway;
    AutoCloseable openMocks;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        completeAppointmentUseCase = new CompleteAppointmentUseCase(appointmentGateway, new AppointmentOutputMapper());
    }

    @Test
    public void shouldCompleteAppointment() {
        // Arrange
        UUID appointmentId = UUID.randomUUID();
        Appointment appointment = AppointmentHelper.createAppointment(appointmentId);

        when(appointmentGateway.findById(any(UUID.class))).thenReturn(Optional.ofNullable(appointment));
        when(appointmentGateway.save(any(Appointment.class))).thenAnswer(returnsFirstArg());

        // Act
        var updatedAppointment = completeAppointmentUseCase.execute(appointmentId);

        // Assert
        verify(appointmentGateway, times(1)).findById(any(UUID.class));
        verify(appointmentGateway, times(1)).save(any(Appointment.class));

        assertThat(updatedAppointment.getStatus()).isEqualTo(Status.COMPLETED);
    }

}