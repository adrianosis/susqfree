package br.com.susqfree.schedule_management.utils;

import br.com.susqfree.schedule_management.domain.model.*;
import br.com.susqfree.schedule_management.infra.gateway.db.mongo.document.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.susqfree.schedule_management.utils.DoctorHelper.createDoctor;
import static br.com.susqfree.schedule_management.utils.DoctorHelper.createDoctorDocument;
import static br.com.susqfree.schedule_management.utils.HealthUnitHelper.createHealthUnit;
import static br.com.susqfree.schedule_management.utils.HealthUnitHelper.createHealthUnitDocument;
import static br.com.susqfree.schedule_management.utils.PatientHelper.createPatient;
import static br.com.susqfree.schedule_management.utils.PatientHelper.createPatientDocument;
import static br.com.susqfree.schedule_management.utils.SpecialtyHelper.createSpecialty;
import static br.com.susqfree.schedule_management.utils.SpecialtyHelper.createSpecialtyDocument;

public class AppointmentHelper {

    public static Appointment createAppointment(UUID id) {
        return Appointment.builder()
                .id(id)
                .dateTime(LocalDateTime.of(2025, 3,1, 8,0))
                .status(Status.AVAILABLE)
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

}
