package br.com.susqfree.schedule_management.utils;

import br.com.susqfree.schedule_management.domain.model.*;
import br.com.susqfree.schedule_management.domain.output.AppointmentOutput;
import br.com.susqfree.schedule_management.infra.gateway.db.mongo.document.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.susqfree.schedule_management.utils.DoctorHelper.*;
import static br.com.susqfree.schedule_management.utils.HealthUnitHelper.*;
import static br.com.susqfree.schedule_management.utils.PatientHelper.*;
import static br.com.susqfree.schedule_management.utils.SpecialtyHelper.*;

public class AppointmentHelper {

    public static Appointment createAppointment(UUID id, Status status) {
        return Appointment.builder()
                .id(id)
                .dateTime(LocalDateTime.of(2025, 3,1, 8,0))
                .status(status)
                .doctor(createDoctor())
                .healthUnit(createHealthUnit())
                .specialty(createSpecialty())
                .patient(createPatient())
                .build();
    }

    public static AppointmentDocument createAppointmentDocument(UUID id) {
        return AppointmentDocument.builder()
                .id(id)
                .dateTime(LocalDateTime.of(2025, 3,1, 8,0))
                .status(Status.AVAILABLE)
                .doctor(createDoctorDocument())
                .healthUnit(createHealthUnitDocument())
                .specialty(createSpecialtyDocument())
                .patient(createPatientDocument())
                .build();
    }

    public static AppointmentOutput createAppointmentOutput(UUID id) {
        return AppointmentOutput.builder()
                .id(id)
                .dateTime(LocalDateTime.of(2025, 3,1, 8,0))
                .status(Status.AVAILABLE)
                .doctor(createDoctorOutput())
                .healthUnit(createHealthUnitOutput())
                .specialty(createSpecialtyOutput())
                .patient(createPatientOutput())
                .build();
    }

}
