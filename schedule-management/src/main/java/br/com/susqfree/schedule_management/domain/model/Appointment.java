package br.com.susqfree.schedule_management.domain.model;

import br.com.susqfree.schedule_management.infra.exception.AppointmentException;
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
        this.validateNotCompleted();
        this.justification = justification;
        this.status = Status.CANCELLED;
    }

    public void schedule(Patient patient) {
        this.validateAvailable();
        this.status = Status.SCHEDULED;
        this.patient = patient;
    }

    public void cancel() {
        this.validateScheduledOrConfirmed();
        this.status = Status.AVAILABLE;
        this.patient = null;
    }

    public void confirm(boolean confirmed) {
        this.validateScheduled();
        if (confirmed) {
            this.status = Status.CONFIRMED;
        } else {
            cancel();
        }
    }

    public void complete() {
        this.validateScheduledOrConfirmed();
        this.status = Status.COMPLETED;
    }

    private void validateAvailable() throws AppointmentException {
        if (this.status != Status.AVAILABLE) {
            throw new AppointmentException("Appointment not available");
        }
    }

    private void validateScheduled() throws AppointmentException {
        if (this.status != Status.SCHEDULED) {
            throw new AppointmentException("Appointment not scheduled");
        }
    }

    private void validateScheduledOrConfirmed() throws AppointmentException {
        if (this.status != Status.SCHEDULED && this.status != Status.CONFIRMED) {
            throw new AppointmentException("Appointment not scheduled or Confirmed");
        }
    }

    private void validateNotCompleted() {
        if (this.status == Status.COMPLETED) {
            throw new AppointmentException("Appointment is Completed");
        }
    }

}
