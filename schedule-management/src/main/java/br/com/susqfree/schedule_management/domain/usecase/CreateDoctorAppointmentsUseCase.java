package br.com.susqfree.schedule_management.domain.usecase;

import br.com.susqfree.schedule_management.domain.gateway.AppointmentGateway;
import br.com.susqfree.schedule_management.domain.gateway.DoctorGateway;
import br.com.susqfree.schedule_management.domain.gateway.HealthUnitGateway;
import br.com.susqfree.schedule_management.domain.gateway.SpecialtyGateway;
import br.com.susqfree.schedule_management.domain.input.CreateAppointmentInput;
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

    public List<AppointmentOutput> execute(List<CreateAppointmentInput> inputs){
        List<Appointment> appointments = new ArrayList<>();
        Doctor doctor = doctorGateway.findById(inputs.get(0).getDoctorId())
                .orElseThrow(() -> new AppointmentException("Doctor not found"));
        HealthUnit healthUnit = healthUnitGateway.findById(inputs.get(0).getHealthUnitId())
                .orElseThrow(() -> new AppointmentException("HealthUnit not found"));
        Specialty specialty = specialtyGateway.findById(inputs.get(0).getSpecialtyId())
                .orElseThrow(() -> new AppointmentException("Specialty not found"));

        for (CreateAppointmentInput input : inputs) {
            LocalDateTime dateTime = input.getStartDateTime();
            while (dateTime.isBefore(input.getEndDateTime())) {
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
