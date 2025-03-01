package br.com.susqfree.schedule_management.infra.gateway.db.mongo;

import br.com.susqfree.schedule_management.MongoTestContainerConfig;
import br.com.susqfree.schedule_management.domain.model.Appointment;
import br.com.susqfree.schedule_management.infra.gateway.db.mongo.mapper.AppointmentDocumentMapper;
import br.com.susqfree.schedule_management.infra.gateway.db.mongo.repository.AppointmentRepository;
import br.com.susqfree.schedule_management.utils.AppointmentHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AppointmentMongoGatewayIT extends MongoTestContainerConfig {

    private AppointmentMongoGateway appointmentMongoGateway;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    public void setup() {
        appointmentMongoGateway = new AppointmentMongoGateway(appointmentRepository, new AppointmentDocumentMapper());
    }

    @Test
    public void shouldSaveAppointment() {
        // Arrange
        var appointment = AppointmentHelper.createAppointment(UUID.randomUUID());

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
//
//    @Test
//    public void shouldSaveAllAppointments() {
//        // Arrange
//        when(appointmentRepository.saveAll(anyList())).thenAnswer(returnsFirstArg());
//
//        var appointment1 = AppointmentHelper.createAppointment(UUID.randomUUID());
//        var appointment2 = AppointmentHelper.createAppointment(UUID.randomUUID());
//
//        // Act
//        var savedAppointments = appointmentMongoGateway.saveAll(List.of(appointment1, appointment2));
//
//        // Assert
//        verify(appointmentRepository, times(1)).saveAll(anyList());
//
//        assertThat(savedAppointments).hasSize(2);
//    }
//
//    @Test
//    public void shouldFindAppointmentById() {
//        // Arrange
//        UUID appointmentId = UUID.randomUUID();
//        var appointment = AppointmentHelper.createAppointmentDocument(appointmentId);
//
//        when(appointmentRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(appointment));
//
//        // Act
//        var optionalFoundAppointment = appointmentMongoGateway.findById(appointmentId);
//
//        // Assert
//        verify(appointmentRepository, times(1)).findById(any(UUID.class));
//
//        assertThat(optionalFoundAppointment.isPresent()).isTrue();
//        optionalFoundAppointment.ifPresent(foundAppointment -> {
//            assertThat(foundAppointment).isInstanceOf(Appointment.class).isNotNull();
//            assertThat(foundAppointment.getId()).isEqualTo(appointment.getId());
//            assertThat(foundAppointment.getDateTime()).isEqualTo(appointment.getDateTime());
//            assertThat(foundAppointment.getStatus()).isEqualTo(appointment.getStatus());
//            assertThat(foundAppointment.getPatient().getId()).isEqualTo(appointment.getPatient().getId());
//            assertThat(foundAppointment.getDoctor().getId()).isEqualTo(appointment.getDoctor().getId());
//            assertThat(foundAppointment.getHealthUnit().getId()).isEqualTo(appointment.getHealthUnit().getId());
//            assertThat(foundAppointment.getSpecialty().getId()).isEqualTo(appointment.getSpecialty().getId());
//        });
//    }
//
//    @Test
//    public void shouldFindAllAppointmentsByPatientIdAndDateTimeBetween() {
//        // Arrange
//        var appointment1 = AppointmentHelper.createAppointmentDocument(UUID.randomUUID());
//        var appointment2 = AppointmentHelper.createAppointmentDocument(UUID.randomUUID());
//
//        when(appointmentRepository.findAllByPatientIdAndDateTimeBetween(any(UUID.class), any(LocalDateTime.class), any(LocalDateTime.class)))
//                .thenReturn(List.of(appointment1, appointment2));
//
//        UUID patientId = UUID.randomUUID();
//        LocalDateTime startDateTine = LocalDateTime.of(2025, 3,1, 8,0);
//        LocalDateTime endDateTine = LocalDateTime.of(2025, 3,10, 22,0);
//
//        // Act
//        var foundAppointments = appointmentMongoGateway.findAllByPatientIdAndDateTimeBetween(patientId, startDateTine, endDateTine);
//
//        // Assert
//        verify(appointmentRepository, times(1))
//                .findAllByPatientIdAndDateTimeBetween(any(UUID.class), any(LocalDateTime.class), any(LocalDateTime.class));
//
//        assertThat(foundAppointments).hasSize(2);
//    }
//
//    @Test
//    public void shouldFindAllAppointmentsByDoctorIdAndDateTimeBetween() {
//        // Arrange
//        var appointment1 = AppointmentHelper.createAppointmentDocument(UUID.randomUUID());
//        var appointment2 = AppointmentHelper.createAppointmentDocument(UUID.randomUUID());
//
//        when(appointmentRepository.findAllByDoctorIdAndDateTimeBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
//                .thenReturn(List.of(appointment1, appointment2));
//
//        long doctorId = 1L;
//        LocalDateTime startDateTine = LocalDateTime.of(2025, 3,1, 8,0);
//        LocalDateTime endDateTine = LocalDateTime.of(2025, 3,10, 22,0);
//
//        // Act
//        var foundAppointments = appointmentMongoGateway.findAllByDoctorIdAndDateTimeBetween(doctorId, startDateTine, endDateTine);
//
//        // Assert
//        verify(appointmentRepository, times(1))
//                .findAllByDoctorIdAndDateTimeBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class));
//
//        assertThat(foundAppointments).hasSize(2);
//    }
//
//    @Test
//    public void shouldFindAllAppointmentsByHealthUnitIdAndDateTimeBetween() {
//        // Arrange
//        var appointment1 = AppointmentHelper.createAppointmentDocument(UUID.randomUUID());
//        var appointment2 = AppointmentHelper.createAppointmentDocument(UUID.randomUUID());
//
//        when(appointmentRepository.findAllByHealthUnitIdAndDateTimeBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class)))
//                .thenReturn(new PageImpl<>(List.of(appointment1, appointment2)));
//
//        long doctorId = 1L;
//        LocalDateTime startDateTine = LocalDateTime.of(2025, 3,1, 8,0);
//        LocalDateTime endDateTine = LocalDateTime.of(2025, 3,10, 22,0);
//
//        // Act
//        var foundAppointments = appointmentMongoGateway.findAllByHealthUnitIdAndDateTimeBetween(doctorId, startDateTine, endDateTine, PageRequest.of(0, 10));
//
//        // Assert
//        verify(appointmentRepository, times(1))
//                .findAllByHealthUnitIdAndDateTimeBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class));
//
//        assertThat(foundAppointments).hasSize(2);
//    }
//
//    @Test
//    public void shouldFindAllAppointmentsAvailableByHealthUnitAndSpecialty() {
//        // Arrange
//        var appointment1 = AppointmentHelper.createAppointmentDocument(UUID.randomUUID());
//        var appointment2 = AppointmentHelper.createAppointmentDocument(UUID.randomUUID());
//
//        when(appointmentRepository.findAllByHealthUnitIdAndSpecialtyIdOrderByDateTime(anyLong(), anyLong(), any(Pageable.class)))
//                .thenReturn(new PageImpl<>(List.of(appointment1, appointment2)));
//
//        long healthUnitId = 1L;
//        long specialtyId = 1L;
//
//        // Act
//        var foundAppointments = appointmentMongoGateway.findAllAvailableByHealthUnitAndSpecialty(healthUnitId, specialtyId, PageRequest.of(0, 10));
//
//        // Assert
//        verify(appointmentRepository, times(1))
//                .findAllByHealthUnitIdAndSpecialtyIdOrderByDateTime(anyLong(), anyLong(), any(Pageable.class));
//
//        assertThat(foundAppointments).hasSize(2);
//    }

}