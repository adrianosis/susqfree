package br.com.susqfree.schedule_management.domain.usecase;

import br.com.susqfree.schedule_management.domain.gateway.AppointmentGateway;
import br.com.susqfree.schedule_management.domain.input.ConfirmAppointmentInput;
import br.com.susqfree.schedule_management.domain.mapper.AppointmentOutputMapper;
import br.com.susqfree.schedule_management.domain.model.Appointment;
import br.com.susqfree.schedule_management.domain.output.AppointmentOutput;
import br.com.susqfree.schedule_management.infra.exception.AppointmentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmAppointmentUseCase {

    private final AppointmentGateway appointmentGateway;
    private final AppointmentOutputMapper mapper;

    public AppointmentOutput execute(ConfirmAppointmentInput input){
        Appointment appointment = appointmentGateway.findById(input.getAppointmentId())
                .orElseThrow(() -> new AppointmentException("Appointment not found"));

        appointment.confirm();

        return mapper.toOutput(appointment);
    }

}
