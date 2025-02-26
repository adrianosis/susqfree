package br.com.susqfree.schedule_management.domain.usecase;

import br.com.susqfree.schedule_management.domain.gateway.AppointmentGateway;
import br.com.susqfree.schedule_management.domain.gateway.PatientGateway;
import br.com.susqfree.schedule_management.domain.input.ScheduleAppointmentInput;
import br.com.susqfree.schedule_management.domain.mapper.AppointmentOutputMapper;
import br.com.susqfree.schedule_management.domain.model.Appointment;
import br.com.susqfree.schedule_management.domain.model.Patient;
import br.com.susqfree.schedule_management.domain.output.AppointmentOutput;
import br.com.susqfree.schedule_management.infra.exception.AppointmentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleAppointmentUseCase {

    private final AppointmentGateway appointmentGateway;
    private final PatientGateway patientGateway;
    private final AppointmentOutputMapper mapper;

    public AppointmentOutput execute(ScheduleAppointmentInput input) {
        Appointment appointment = appointmentGateway.findById(input.getAppointmentId())
                .orElseThrow(() -> new AppointmentException("Appointment not found"));
        Patient patient = patientGateway.findById(input.getAppointmentId())
                .orElseThrow(() -> new AppointmentException("Patient not found"));

        appointment.schedule(patient);

        return mapper.toOutput(appointment);
    }

}
