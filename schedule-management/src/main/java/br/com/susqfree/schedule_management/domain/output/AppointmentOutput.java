package br.com.susqfree.schedule_management.domain.output;

import br.com.susqfree.schedule_management.domain.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentOutput {

    private UUID id;
    private LocalDateTime dateTime;
    private Status status;
    private String justification;
    private PatientOutput patient;
    private DoctorOutput doctor;
    private HealthUnitOutput healthUnit;
    private SpecialtyOutput specialty;

}
