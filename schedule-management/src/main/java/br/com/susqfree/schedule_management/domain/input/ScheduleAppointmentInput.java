package br.com.susqfree.schedule_management.domain.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleAppointmentInput {

    private UUID appointmentId;
    private UUID patientId;

}
