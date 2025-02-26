package br.com.susqfree.schedule_management.domain.mapper;

import br.com.susqfree.schedule_management.domain.model.*;
import br.com.susqfree.schedule_management.domain.output.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppointmentOutputMapper {

    public AppointmentOutput toOutput(Appointment appointment) {

        PatientOutput patientOutput = Optional.ofNullable(appointment.getPatient())
                .map(patient -> PatientOutput.builder()
                        .id(patient.getId())
                        .name(patient.getName())
                        .cpf(patient.getCpf())
                        .susNumber(patient.getSusNumber())
                        .build()).orElse(null);

        DoctorOutput doctorOutput = DoctorOutput.builder()
                .id(appointment.getDoctor().getId())
                .name(appointment.getDoctor().getName())
                .crm(appointment.getDoctor().getCrm())
                .build();

        HealthUnitOutput healthUnitOutput = HealthUnitOutput.builder()
                .id(appointment.getHealthUnit().getId())
                .name(appointment.getHealthUnit().getName())
                .build();

        SpecialtyOutput specialtyOutput = SpecialtyOutput.builder()
                .id(appointment.getSpecialty().getId())
                .name(appointment.getSpecialty().getName())
                .build();

        return AppointmentOutput.builder()
                .id(appointment.getId())
                .dateTime(appointment.getDateTime())
                .status(appointment.getStatus())
                .justification(appointment.getJustification())
                .patient(patientOutput)
                .doctor(doctorOutput)
                .healthUnit(healthUnitOutput)
                .specialty(specialtyOutput)
                .build();
    }

}
