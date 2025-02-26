package br.com.susqfree.schedule_management.domain.usecase;

import br.com.susqfree.schedule_management.domain.gateway.AppointmentGateway;
import br.com.susqfree.schedule_management.domain.gateway.DoctorGateway;
import br.com.susqfree.schedule_management.domain.gateway.HealthUnitGateway;
import br.com.susqfree.schedule_management.domain.gateway.SpecialtyGateway;
import br.com.susqfree.schedule_management.domain.input.CreateAppointmentInput;
import br.com.susqfree.schedule_management.domain.mapper.AppointmentOutputMapper;
import br.com.susqfree.schedule_management.domain.model.Doctor;
import br.com.susqfree.schedule_management.domain.model.HealthUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CreateDoctorAppointmentsUseCaseTest {

    private CreateDoctorAppointmentsUseCase createDoctorAppointmentsUseCase;

    @Mock
    private AppointmentGateway appointmentGateway;
    @Mock
    private DoctorGateway doctorGateway;
    @Mock
    private HealthUnitGateway healthUnitGateway;
    @Mock
    private SpecialtyGateway specialtyGateway;
    AutoCloseable openMocks;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        createDoctorAppointmentsUseCase = new CreateDoctorAppointmentsUseCase(
                appointmentGateway, doctorGateway, healthUnitGateway, specialtyGateway, new AppointmentOutputMapper());
    }

    @Test
    public void shouldCreateAppointments() {
        // Arrange
        Doctor doctor = new Doctor();
        HealthUnit healthUnit = new HealthUnit();

        when(doctorGateway.findById(anyLong())).thenReturn(Optional.of(doctor));
        when(healthUnitGateway.findById(anyLong())).thenReturn(Optional.of(healthUnit));
        when(appointmentGateway.saveAll(anyList())).thenAnswer(returnsFirstArg());

        var input = CreateAppointmentInput.builder()
                .doctorId(1L)
                .healthUnitId(1L)
                .specialtyId(1L)
                .startDateTime(LocalDateTime.of(2025,2,24,8,0))
                .endDateTime(LocalDateTime.of(2025,2,24,12,0))
                .build();
        var input2 = CreateAppointmentInput.builder()
                .doctorId(1L)
                .healthUnitId(1L)
                .specialtyId(1L)
                .startDateTime(LocalDateTime.of(2025,2,24,13,0))
                .endDateTime(LocalDateTime.of(2025,2,24,17,0))
                .build();
        var inputs = List.of(input,input2);

        // Act
        var savedAppointments = createDoctorAppointmentsUseCase.execute(inputs);

        // Assert
        verify(doctorGateway, times(1)).findById(anyLong());
        verify(healthUnitGateway, times(1)).findById(anyLong());
        verify(appointmentGateway, times(1)).saveAll(anyList());
        assertThat(savedAppointments).hasSize(16);
    }

}