package br.com.susqfree.schedule_management.domain.usecase;

import br.com.susqfree.schedule_management.domain.gateway.AppointmentGateway;
import br.com.susqfree.schedule_management.domain.mapper.AppointmentOutputMapper;
import br.com.susqfree.schedule_management.domain.output.AppointmentOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAppointmentsAvailableByHealthUnitAndSpecialtyUseCase {

    private final AppointmentGateway appointmentGateway;
    private final AppointmentOutputMapper mapper;

    public Page<AppointmentOutput> execute(long healthUnitId, long specialtyId, Pageable pageable) {
        var appointmentsPage = appointmentGateway.findAvailableByHealthUnitAndSpecialty(healthUnitId, specialtyId, pageable);

        return appointmentsPage.map(mapper::toOutput);
    }

}
