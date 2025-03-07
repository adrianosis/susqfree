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
import br.com.susqfree.schedule_management.domain.output.AppointmentOutput;
import br.com.susqfree.schedule_management.infra.exception.AppointmentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateDoctorAppointmentsUseCase {

    private final AppointmentGateway appointmentGateway;
    private final DoctorGateway doctorGateway;
    private final HealthUnitGateway healthUnitGateway;
    private final SpecialtyGateway specialtyGateway;
    private final AppointmentOutputMapper mapper;

    public List<AppointmentOutput> execute(CreateAppointmentInput input) {
        List<Appointment> appointments = new ArrayList<>();
        Doctor doctor = doctorGateway.findById(input.getDoctorId())
                .orElseThrow(() -> new AppointmentException("Doctor not found"));
        HealthUnit healthUnit = healthUnitGateway.findById(input.getHealthUnitId())
                .orElseThrow(() -> new AppointmentException("HealthUnit not found"));
        Specialty specialty = specialtyGateway.findById(input.getSpecialtyId())
                .orElseThrow(() -> new AppointmentException("Specialty not found"));


        for (CreateAppointmentPeriodInput period : input.getPeriods()) {
            var existingAppointments = appointmentGateway.findAllByDoctorIdAndDateTimeBetween(doctor.getId(), period.getStartDateTime(), period.getEndDateTime());

            if (!existingAppointments.isEmpty()) {
                throw new AppointmentException("Already existing appointment");
            }

            LocalDateTime dateTime = period.getStartDateTime();
            while (dateTime.isBefore(period.getEndDateTime())) {
                Appointment appointment = Appointment.builder()
                        .dateTime(dateTime)
                        .doctor(doctor)
                        .healthUnit(healthUnit)
                        .specialty(specialty)
                        .build();
                appointment.create();
                appointments.add(appointment);

                dateTime = dateTime.plusMinutes(30);
            }
        }

        appointments = appointmentGateway.saveAll(appointments);

        return appointments.stream().map(mapper::toOutput).toList();
    }

}
