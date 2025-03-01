package br.com.susqfree.schedule_management.domain.usecase;

import br.com.susqfree.schedule_management.domain.gateway.AppointmentGateway;
import br.com.susqfree.schedule_management.domain.mapper.AppointmentOutputMapper;
import br.com.susqfree.schedule_management.domain.model.Appointment;
import br.com.susqfree.schedule_management.utils.AppointmentHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class FindAppointmentsAvailableByHealthUnitAndSpecialtyUseCaseTest {

    private FindAppointmentsAvailableByHealthUnitAndSpecialtyUseCase findAppointmentsAvailableByHealthUnitAndSpecialtyUseCase;

    @Mock
    private AppointmentGateway appointmentGateway;
    AutoCloseable openMocks;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        findAppointmentsAvailableByHealthUnitAndSpecialtyUseCase = new FindAppointmentsAvailableByHealthUnitAndSpecialtyUseCase(appointmentGateway, new AppointmentOutputMapper());
    }

    @Test
    public void shouldFindAppointmentsAvailableByHealthUnitAndSpecialty() {
        // Arrange
        Appointment appointment1 = AppointmentHelper.createAppointment(UUID.randomUUID());
        Appointment appointment2 = AppointmentHelper.createAppointment(UUID.randomUUID());

        when(appointmentGateway.findAllAvailableByHealthUnitAndSpecialty(anyLong(), anyLong(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(appointment1, appointment2)));

        long healthUnitId = 1L;
        long specialtyId = 1L;

        // Act
        var foundAppointments = findAppointmentsAvailableByHealthUnitAndSpecialtyUseCase
                .execute(healthUnitId, specialtyId, PageRequest.of(0, 10));

        // Assert
        verify(appointmentGateway, times(1)).findAllAvailableByHealthUnitAndSpecialty(anyLong(), anyLong(), any(Pageable.class));

        assertThat(foundAppointments).hasSize(2);
    }

}