package br.com.susqfree.schedule_management.domain.usecase;

import br.com.susqfree.schedule_management.domain.gateway.AppointmentGateway;
import br.com.susqfree.schedule_management.domain.gateway.DoctorGateway;
import br.com.susqfree.schedule_management.domain.gateway.HealthUnitGateway;
import br.com.susqfree.schedule_management.domain.gateway.SpecialtyGateway;
import br.com.susqfree.schedule_management.domain.input.CreateAppointmentInput;
import br.com.susqfree.schedule_management.domain.input.CreateAppointmentPeriodInput;
import br.com.susqfree.schedule_management.domain.mapper.AppointmentOutputMapper;
import br.com.susqfree.schedule_management.domain.model.Appointment;
import br.com.susqfree.schedule_management.domain.model.Doctor;
import br.com.susqfree.schedule_management.domain.model.HealthUnit;
import br.com.susqfree.schedule_management.domain.model.Specialty;
import br.com.susqfree.schedule_management.infra.exception.AppointmentException;
import br.com.susqfree.schedule_management.utils.DoctorHelper;
import br.com.susqfree.schedule_management.utils.HealthUnitHelper;
import br.com.susqfree.schedule_management.utils.SpecialtyHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        Doctor doctor = DoctorHelper.createDoctor();
        HealthUnit healthUnit = HealthUnitHelper.createHealthUnit();
        Specialty specialty = SpecialtyHelper.createSpecialty();

        when(doctorGateway.findById(anyLong())).thenReturn(Optional.of(doctor));
        when(healthUnitGateway.findById(anyLong())).thenReturn(Optional.of(healthUnit));
        when(specialtyGateway.findById(anyLong())).thenReturn(Optional.of(specialty));
        when(appointmentGateway.findAllByDoctorIdAndDateTimeBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of());
        when(appointmentGateway.saveAll(anyList())).thenAnswer(returnsFirstArg());


        var input = CreateAppointmentInput.builder()
                .doctorId(1L)
                .healthUnitId(1L)
                .specialtyId(1L)
                .periods(List.of(CreateAppointmentPeriodInput.builder()
                        .startDateTime(LocalDateTime.of(2025,2,24,8,0))
                        .endDateTime(LocalDateTime.of(2025,2,24,12,0))
                        .build(),
                        CreateAppointmentPeriodInput.builder()
                                .startDateTime(LocalDateTime.of(2025,2,24,13,0))
                                .endDateTime(LocalDateTime.of(2025,2,24,17,0))
                                .build()
                )).build();

        // Act
        var savedAppointments = createDoctorAppointmentsUseCase.execute(input);

        // Assert
        verify(doctorGateway, times(1)).findById(anyLong());
        verify(healthUnitGateway, times(1)).findById(anyLong());
        verify(specialtyGateway, times(1)).findById(anyLong());
        verify(appointmentGateway, times(2)).findAllByDoctorIdAndDateTimeBetween(
                anyLong(), any(LocalDateTime.class), any(LocalDateTime.class));
        verify(appointmentGateway, times(1)).saveAll(anyList());

        assertThat(savedAppointments).hasSize(16);
    }

    @Test
    public void shouldThrowException_WhenCreateAppointment_WithExistingPeriod() {
        // Arrange
        Doctor doctor = DoctorHelper.createDoctor();
        HealthUnit healthUnit = HealthUnitHelper.createHealthUnit();
        Specialty specialty = SpecialtyHelper.createSpecialty();

        when(doctorGateway.findById(anyLong())).thenReturn(Optional.of(doctor));
        when(healthUnitGateway.findById(anyLong())).thenReturn(Optional.of(healthUnit));
        when(specialtyGateway.findById(anyLong())).thenReturn(Optional.of(specialty));
        when(appointmentGateway.findAllByDoctorIdAndDateTimeBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(new Appointment()));

        var input = CreateAppointmentInput.builder()
                .doctorId(1L)
                .healthUnitId(1L)
                .specialtyId(1L)
                .periods(List.of(CreateAppointmentPeriodInput.builder()
                        .startDateTime(LocalDateTime.of(2025,2,24,8,0))
                        .endDateTime(LocalDateTime.of(2025,2,24,12,0))
                        .build()
                )).build();

        // Act
        // Assert
        assertThatThrownBy(
                () -> createDoctorAppointmentsUseCase.execute(input))
                .isInstanceOf(AppointmentException.class)
                .hasMessage("Already existing appointment");

        verify(doctorGateway, times(1)).findById(anyLong());
        verify(healthUnitGateway, times(1)).findById(anyLong());
        verify(specialtyGateway, times(1)).findById(anyLong());
        verify(appointmentGateway, times(1)).findAllByDoctorIdAndDateTimeBetween(
                anyLong(), any(LocalDateTime.class), any(LocalDateTime.class));
    }

}