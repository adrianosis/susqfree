package br.com.susqfree.schedule_management.infra.gateway.db.mongo.document;

import br.com.susqfree.schedule_management.domain.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class AppointmentDocument {

    private UUID id;
    private LocalDateTime dateTime;
    private Status status;
    private String justification;
    private PatientDocument patient;
    private DoctorDocument doctor;
    private HealthUnitDocument healthUnit;
    private SpecialtyDocument specialty;

}
