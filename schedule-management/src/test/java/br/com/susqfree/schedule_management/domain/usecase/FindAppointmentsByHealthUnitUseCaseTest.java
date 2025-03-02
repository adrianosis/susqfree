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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class FindAppointmentsByHealthUnitUseCaseTest {

    private FindAppointmentsByHealthUnitUseCase findAppointmentsByHealthUnitUseCase;

    @Mock
    private AppointmentGateway appointmentGateway;
    AutoCloseable openMocks;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        findAppointmentsByHealthUnitUseCase = new FindAppointmentsByHealthUnitUseCase(appointmentGateway, new AppointmentOutputMapper());
    }

    @Test
    public void shouldFindAppointmentsByHealthUnit() {
        // Arrange
        Appointment appointment1 = AppointmentHelper.createAppointment(UUID.randomUUID(), Status.AVAILABLE);
        Appointment appointment2 = AppointmentHelper.createAppointment(UUID.randomUUID(), Status.AVAILABLE);

        when(appointmentGateway.findAllByHealthUnitIdAndDateTimeBetween(
                anyLong(), any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(appointment1, appointment2)));

        long healthUnitId = 1L;
        LocalDateTime startDateTime = LocalDateTime.of(2025, 3, 1, 8, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2025, 3, 10, 22, 0);

        // Act
        var foundAppointments = findAppointmentsByHealthUnitUseCase.execute(healthUnitId, startDateTime, endDateTime, PageRequest.of(0, 10));

        // Assert
        verify(appointmentGateway, times(1))
                .findAllByHealthUnitIdAndDateTimeBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class));

        assertThat(foundAppointments).hasSize(2);
    }

}