package br.com.susqfree.schedule_management.infra.gateway.db.mongo.mapper;

import br.com.susqfree.schedule_management.domain.model.*;
import br.com.susqfree.schedule_management.infra.gateway.db.mongo.document.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppointmentDocumentMapper {

    public AppointmentDocument toDocument(Appointment appointment) {
        PatientDocument patientDocument = Optional.ofNullable(appointment.getPatient())
                .map(patient -> PatientDocument.builder()
                        .id(patient.getId())
                        .name(patient.getName())
                        .cpf(patient.getCpf())
                        .susNumber(patient.getSusNumber())
                        .build()).orElse(null);

        DoctorDocument doctorDocument = DoctorDocument.builder()
                .id(appointment.getDoctor().getId())
                .name(appointment.getDoctor().getName())
                .crm(appointment.getDoctor().getCrm())
                .build();

        HealthUnitDocument healthUnitDocument = HealthUnitDocument.builder()
                .id(appointment.getHealthUnit().getId())
                .name(appointment.getHealthUnit().getName())
                .build();

        SpecialtyDocument specialtyDocument = SpecialtyDocument.builder()
                .id(appointment.getSpecialty().getId())
                .name(appointment.getSpecialty().getName())
                .build();

        return AppointmentDocument.builder()
                .id(appointment.getId())
                .dateTime(appointment.getDateTime())
                .status(appointment.getStatus())
                .justification(appointment.getJustification())
                .patient(patientDocument)
                .doctor(doctorDocument)
                .healthUnit(healthUnitDocument)
                .specialty(specialtyDocument)
                .build();
    }

    public Appointment toDomain(AppointmentDocument appointmentDocument) {
        Patient patient = Optional.ofNullable(appointmentDocument.getPatient())
                .map(patientDocument -> Patient.builder()
                        .id(patientDocument.getId())
                        .name(patientDocument.getName())
                        .cpf(patientDocument.getCpf())
                        .susNumber(patientDocument.getSusNumber())
                        .build()).orElse(null);

        Doctor doctor = Doctor.builder()
                .id(appointmentDocument.getDoctor().getId())
                .name(appointmentDocument.getDoctor().getName())
                .crm(appointmentDocument.getDoctor().getCrm())
                .build();

        HealthUnit healthUnit = HealthUnit.builder()
                .id(appointmentDocument.getHealthUnit().getId())
                .name(appointmentDocument.getHealthUnit().getName())
                .build();

        Specialty specialty = Specialty.builder()
                .id(appointmentDocument.getSpecialty().getId())
                .name(appointmentDocument.getSpecialty().getName())
                .build();

        return Appointment.builder()
                .id(appointmentDocument.getId())
                .dateTime(appointmentDocument.getDateTime())
                .status(appointmentDocument.getStatus())
                .justification(appointmentDocument.getJustification())
                .patient(patient)
                .doctor(doctor)
                .healthUnit(healthUnit)
                .specialty(specialty)
                .build();
    }

}
