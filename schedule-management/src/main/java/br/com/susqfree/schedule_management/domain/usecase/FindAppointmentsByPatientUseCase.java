package br.com.susqfree.schedule_management.domain.usecase;

import br.com.susqfree.schedule_management.domain.gateway.AppointmentGateway;
import br.com.susqfree.schedule_management.domain.mapper.AppointmentOutputMapper;
import br.com.susqfree.schedule_management.domain.output.AppointmentOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindAppointmentsByPatientUseCase {

    private final AppointmentGateway appointmentGateway;
    private final AppointmentOutputMapper mapper;

    public List<AppointmentOutput> execute(UUID patientId, LocalDateTime startDateTime, LocalDateTime endDateDate){
        var appointments = appointmentGateway.findAllByPatientIdAndDateTimeBetween(patientId, startDateTime, endDateDate);

        return appointments.stream().map(mapper::toOutput).toList();
    }

}
