package br.com.susqfree.schedule_management.domain.input;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentInput {

    private long doctorId;
    private long healthUnitId;
    private long specialtyId;
    @NotEmpty
    private List<CreateAppointmentPeriodInput> periods;

}
