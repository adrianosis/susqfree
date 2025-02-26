package br.com.susqfree.schedule_management.domain.usecase;

import br.com.susqfree.schedule_management.domain.gateway.AppointmentGateway;
import br.com.susqfree.schedule_management.domain.mapper.AppointmentOutputMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        // Act
        // Assert
    }

}