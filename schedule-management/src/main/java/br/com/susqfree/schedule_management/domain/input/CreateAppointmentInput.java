package br.com.susqfree.schedule_management.domain.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentInput {

    private long doctorId;
    private long healthUnitId;
    private long specialtyId;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

}
