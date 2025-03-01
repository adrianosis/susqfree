package br.com.susqfree.schedule_management.domain.usecase;

import br.com.susqfree.schedule_management.domain.gateway.AppointmentGateway;
import br.com.susqfree.schedule_management.domain.mapper.AppointmentOutputMapper;
import br.com.susqfree.schedule_management.domain.model.Appointment;
import br.com.susqfree.schedule_management.domain.output.AppointmentOutput;
import br.com.susqfree.schedule_management.infra.exception.AppointmentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelAppointmentUseCase {

    private final AppointmentGateway appointmentGateway;
    private final AppointmentOutputMapper mapper;

    public AppointmentOutput execute(UUID appointmentId) {
        Appointment appointment = appointmentGateway.findById(appointmentId)
                .orElseThrow(() -> new AppointmentException("Appointment not found"));

        appointment.cancel();
        appointment = appointmentGateway.save(appointment);

        return mapper.toOutput(appointment);
    }

}
