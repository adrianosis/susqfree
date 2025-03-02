package br.com.susqfree.schedule_management.domain.input;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmAppointmentInput {

    @NotNull
    private UUID appointmentId;
    private boolean confirmed;

}
