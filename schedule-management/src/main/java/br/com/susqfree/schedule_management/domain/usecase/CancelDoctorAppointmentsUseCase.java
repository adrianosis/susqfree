package br.com.susqfree.schedule_management.domain.usecase;

import br.com.susqfree.schedule_management.domain.gateway.AppointmentGateway;
import br.com.susqfree.schedule_management.domain.input.CancelDoctorAppointmentsInput;
import br.com.susqfree.schedule_management.domain.mapper.AppointmentOutputMapper;
import br.com.susqfree.schedule_management.domain.output.AppointmentOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CancelDoctorAppointmentsUseCase {

    private final AppointmentGateway appointmentGateway;
    private final AppointmentOutputMapper mapper;

    public List<AppointmentOutput> execute(CancelDoctorAppointmentsInput input) {
        var appointments = appointmentGateway.findAllByDoctorIdAndDateTimeBetween(input.getDoctorId(), input.getStartDateTime(), input.getEndDateTime());

        appointments.forEach(appointment -> appointment.cancel(input.getJustification()));
        appointments = appointmentGateway.saveAll(appointments);

        return appointments.stream().map(mapper::toOutput).toList();
    }

}
