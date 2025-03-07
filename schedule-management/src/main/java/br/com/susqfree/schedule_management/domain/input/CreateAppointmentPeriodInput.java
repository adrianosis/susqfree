package br.com.susqfree.schedule_management.domain.input;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentPeriodInput {

    @NotNull(message = "StartDateTime cannot be null")
    private LocalDateTime startDateTime;
    @NotNull(message = "StartDateTime cannot be null")
    private LocalDateTime endDateTime;

}
