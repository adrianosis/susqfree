package br.com.susqfree.schedule_management.domain.usecase;

import br.com.susqfree.schedule_management.domain.gateway.AppointmentGateway;
import br.com.susqfree.schedule_management.domain.input.CancelDoctorAppointmentsInput;
import br.com.susqfree.schedule_management.domain.mapper.AppointmentOutputMapper;
import br.com.susqfree.schedule_management.domain.model.Appointment;
import br.com.susqfree.schedule_management.domain.model.Status;
import br.com.susqfree.schedule_management.utils.AppointmentHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CancelDoctorAppointmentsUseCaseTest {

    private CancelDoctorAppointmentsUseCase cancelDoctorAppointmentsUseCase;

    @Mock
    private AppointmentGateway appointmentGateway;
    AutoCloseable openMocks;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        cancelDoctorAppointmentsUseCase = new CancelDoctorAppointmentsUseCase(appointmentGateway, new AppointmentOutputMapper());
    }

    @Test
    public void shouldCancelDoctorAppointments() {
        // Arrange
        long doctorId = 1L;
        Appointment appointment = AppointmentHelper.createAppointment(UUID.randomUUID());

        when(appointmentGateway.findAllByDoctorIdAndDateTimeBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(appointment));
        when(appointmentGateway.saveAll(anyList())).thenAnswer(returnsFirstArg());

        var input = CancelDoctorAppointmentsInput.builder()
                .doctorId(doctorId)
                .startDateTime(LocalDateTime.of(2025,3,1,8,0))
                .endDateTime(LocalDateTime.of(2025,3,1,18,0))
                .justification("Personal commitment")
                .build();

        // Act
        var canceledAppointments = cancelDoctorAppointmentsUseCase.execute(input);

        // Assert
        verify(appointmentGateway, times(1)).findAllByDoctorIdAndDateTimeBetween(
                anyLong(), any(LocalDateTime.class), any(LocalDateTime.class));
        verify(appointmentGateway, times(1)).saveAll(anyList());

        assertThat(canceledAppointments).hasSize(1);
        assertThat(canceledAppointments.get(0).getStatus()).isEqualTo(Status.CANCELLED);
        assertThat(canceledAppointments.get(0).getJustification()).isEqualTo("Personal commitment");

    }

}