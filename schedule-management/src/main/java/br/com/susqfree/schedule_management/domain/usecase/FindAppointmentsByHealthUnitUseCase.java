package br.com.susqfree.schedule_management.domain.usecase;

import br.com.susqfree.schedule_management.domain.gateway.AppointmentGateway;
import br.com.susqfree.schedule_management.domain.mapper.AppointmentOutputMapper;
import br.com.susqfree.schedule_management.domain.output.AppointmentOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FindAppointmentsByHealthUnitUseCase {

    private final AppointmentGateway appointmentGateway;
    private final AppointmentOutputMapper mapper;

    public Page<AppointmentOutput> execute(long healthUnitId, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable) {
        var appointments = appointmentGateway.findAllByHealthUnitIdAndDateTimeBetween(healthUnitId, startDateTime, endDateTime, pageable);

        return appointments.map(mapper::toOutput);
    }

}
