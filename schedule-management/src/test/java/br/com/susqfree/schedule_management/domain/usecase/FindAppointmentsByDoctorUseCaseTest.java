package br.com.susqfree.schedule_management.domain.usecase;

import br.com.susqfree.schedule_management.domain.gateway.AppointmentGateway;
import br.com.susqfree.schedule_management.domain.mapper.AppointmentOutputMapper;
import br.com.susqfree.schedule_management.domain.model.Appointment;
import br.com.susqfree.schedule_management.utils.AppointmentHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FindAppointmentsByDoctorUseCaseTest {

    private FindAppointmentsByDoctorUseCase findAppointmentsByDoctorUseCase;

    @Mock
    private AppointmentGateway appointmentGateway;
    AutoCloseable openMocks;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        findAppointmentsByDoctorUseCase = new FindAppointmentsByDoctorUseCase(appointmentGateway, new AppointmentOutputMapper());
    }

    @Test
    public void shouldFindAppointmentsByDoctor() {
        // Arrange
        Appointment appointment1 = AppointmentHelper.createAppointment(UUID.randomUUID());
        Appointment appointment2 = AppointmentHelper.createAppointment(UUID.randomUUID());

        when(appointmentGateway.findAllByDoctorIdAndDateTimeBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(appointment1, appointment2));

        long doctorId = 1L;
        LocalDateTime startDateTine = LocalDateTime.of(2025, 3, 1, 8, 0);
        LocalDateTime endDateTine = LocalDateTime.of(2025, 3, 10, 22, 0);


        // Act
        var foundAppointments = findAppointmentsByDoctorUseCase.execute(doctorId, startDateTine, endDateTine);

        // Assert
        verify(appointmentGateway, times(1))
                .findAllByDoctorIdAndDateTimeBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class));

        assertThat(foundAppointments).hasSize(2);
    }

}