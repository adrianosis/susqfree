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
public class CancelDoctorAppointmentsInput {

    private long doctorId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String justification;

}
