package br.com.susqfree.schedule_management.domain.usecase;

import br.com.susqfree.schedule_management.domain.gateway.AppointmentGateway;
import br.com.susqfree.schedule_management.domain.mapper.AppointmentOutputMapper;
import br.com.susqfree.schedule_management.domain.output.AppointmentOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAppointmentsByDoctorUseCase {

    private final AppointmentGateway appointmentGateway;
    private final AppointmentOutputMapper mapper;

    public List<AppointmentOutput> execute(long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        var appointments = appointmentGateway.findAllByDoctorIdAndDateTimeBetween(doctorId, startDateTime, endDateTime);

        return appointments.stream().map(mapper::toOutput).toList();
    }

}
