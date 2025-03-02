package br.com.susqfree.schedule_management.infra.gateway.db.mongo;

import br.com.susqfree.schedule_management.domain.model.Appointment;
import br.com.susqfree.schedule_management.domain.model.Status;
import br.com.susqfree.schedule_management.infra.gateway.db.mongo.repository.AppointmentRepository;
import br.com.susqfree.schedule_management.utils.AppointmentHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AppointmentMongoGatewayIT extends MongoTestContainerConfig {

    @Autowired
    private AppointmentMongoGateway appointmentMongoGateway;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    public void setup() {
        var appointment1 = AppointmentHelper.createAppointment(UUID.randomUUID(), Status.AVAILABLE);
        var appointment2 = AppointmentHelper.createAppointment(UUID.randomUUID(), Status.AVAILABLE);
        var appointment3 = AppointmentHelper.createAppointment(UUID.randomUUID(), Status.AVAILABLE);

        appointmentMongoGateway.saveAll(List.of(appointment1, appointment2, appointment3));
    }

    @AfterEach
    public void tearDown() {
        appointmentRepository.deleteAll();
    }

    @Test
    public void shouldSaveAppointment() {
        // Arrange
        var appointment = AppointmentHelper.createAppointment(UUID.randomUUID(), Status.AVAILABLE);

        // Act
        var savedAppointment = appointmentMongoGateway.save(appointment);

        // Assert
        assertThat(savedAppointment).isInstanceOf(Appointment.class).isNotNull();
        assertThat(savedAppointment.getId()).isEqualTo(appointment.getId());
        assertThat(savedAppointment.getDateTime()).isEqualTo(appointment.getDateTime());
        assertThat(savedAppointment.getStatus()).isEqualTo(appointment.getStatus());
        assertThat(savedAppointment.getPatient().getId()).isEqualTo(appointment.getPatient().getId());
        assertThat(savedAppointment.getDoctor().getId()).isEqualTo(appointment.getDoctor().getId());
        assertThat(savedAppointment.getHealthUnit().getId()).isEqualTo(appointment.getHealthUnit().getId());
        assertThat(savedAppointment.getSpecialty().getId()).isEqualTo(appointment.getSpecialty().getId());
    }

    @Test
    public void shouldSaveAllAppointments() {
        // Arrange
        var appointment1 = AppointmentHelper.createAppointment(UUID.randomUUID(), Status.AVAILABLE);
        var appointment2 = AppointmentHelper.createAppointment(UUID.randomUUID(), Status.AVAILABLE);

        // Act
        var savedAppointments = appointmentMongoGateway.saveAll(List.of(appointment1, appointment2));

        // Assert
        assertThat(savedAppointments).hasSize(2);
    }

    @Test
    public void shouldFindAppointmentById() {
        // Arrange
        UUID appointmentId = UUID.randomUUID();
        var appointment = AppointmentHelper.createAppointment(appointmentId, Status.AVAILABLE);
        appointmentMongoGateway.save(appointment);

        // Act
        var optionalFoundAppointment = appointmentMongoGateway.findById(appointmentId);

        // Assert
        assertThat(optionalFoundAppointment.isPresent()).isTrue();
        optionalFoundAppointment.ifPresent(foundAppointment -> {
            assertThat(foundAppointment).isInstanceOf(Appointment.class).isNotNull();
            assertThat(foundAppointment.getId()).isEqualTo(appointment.getId());
            assertThat(foundAppointment.getDateTime()).isEqualTo(appointment.getDateTime());
            assertThat(foundAppointment.getStatus()).isEqualTo(appointment.getStatus());
            assertThat(foundAppointment.getPatient().getId()).isEqualTo(appointment.getPatient().getId());
            assertThat(foundAppointment.getDoctor().getId()).isEqualTo(appointment.getDoctor().getId());
            assertThat(foundAppointment.getHealthUnit().getId()).isEqualTo(appointment.getHealthUnit().getId());
            assertThat(foundAppointment.getSpecialty().getId()).isEqualTo(appointment.getSpecialty().getId());
        });
    }

    @Test
    public void shouldFindAllAppointmentsByPatientIdAndDateTimeBetween() {
        // Arrange
        UUID patientId = UUID.fromString("67f74a7b-08a6-4680-81af-4ceb036bb387");
        LocalDateTime startDateTine = LocalDateTime.of(2025, 3, 1, 8, 0);
        LocalDateTime endDateTine = LocalDateTime.of(2025, 3, 10, 22, 0);


        // Act
        var foundAppointments = appointmentMongoGateway.findAllByPatientIdAndDateTimeBetween(patientId, startDateTine, endDateTine);

        // Assert
        assertThat(foundAppointments).hasSize(3);
    }

    @Test
    public void shouldFindAllAppointmentsByDoctorIdAndDateTimeBetween() {
        // Arrange
        long doctorId = 1L;
        LocalDateTime startDateTine = LocalDateTime.of(2025, 3, 1, 8, 0);
        LocalDateTime endDateTine = LocalDateTime.of(2025, 3, 10, 22, 0);

        // Act
        var foundAppointments = appointmentMongoGateway.findAllByDoctorIdAndDateTimeBetween(doctorId, startDateTine, endDateTine);

        // Assert
        assertThat(foundAppointments).hasSize(3);
    }

    @Test
    public void shouldFindAllAppointmentsByHealthUnitIdAndDateTimeBetween() {
        // Arrange
        long healthUnitId = 1L;
        LocalDateTime startDateTine = LocalDateTime.of(2025, 3, 1, 8, 0);
        LocalDateTime endDateTine = LocalDateTime.of(2025, 3, 10, 22, 0);

        // Act
        var foundAppointments = appointmentMongoGateway.findAllByHealthUnitIdAndDateTimeBetween(healthUnitId, startDateTine, endDateTine, PageRequest.of(0, 10));

        // Assert
        assertThat(foundAppointments).hasSize(3);
    }

    @Test
    public void shouldFindAllAppointmentsAvailableByHealthUnitAndSpecialty() {
        // Arrange
        long healthUnitId = 1L;
        long specialtyId = 1L;

        // Act
        var foundAppointments = appointmentMongoGateway.findAllAvailableByHealthUnitAndSpecialty(healthUnitId, specialtyId, PageRequest.of(0, 10));

        // Assert
        assertThat(foundAppointments).hasSize(3);
    }

}