package br.com.susqfree.schedule_management.domain.model;

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
public class Appointment {

    private UUID id;
    private LocalDateTime dateTime;
    private Status status;
    private String justification;
    private Patient patient;
    private Doctor doctor;
    private HealthUnit healthUnit;
    private Specialty specialty;

    public void create() {
        this.id = UUID.randomUUID();
        this.status = Status.AVAILABLE;
    }

    public void cancel(String justification) {
        this.justification = justification;
        this.status = Status.CANCELLED;
    }

    public void schedule(Patient patient) {
        this.status = Status.SCHEDULED;
        this.patient = patient;
    }

    public void cancel() {
        this.status = Status.AVAILABLE;
        this.patient = null;
    }

    public void confirm() {
        this.status = Status.CONFIRMED;
    }

    public void complete() {
        this.status = Status.COMPLETED;

    }

}
