package br.com.susqfree.schedule_management.domain.gateway;

import br.com.susqfree.schedule_management.domain.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentGateway {

    Appointment save(Appointment appointment);

    List<Appointment> saveAll(List<Appointment> appointments);

    Optional<Appointment> findById(UUID appointmentId);

    List<Appointment> findAllByPatientIdAndDateTimeBetween(UUID patientId, LocalDateTime startDate, LocalDateTime endDate);

    List<Appointment> findAllByDoctorIdAndDateTimeBetween(long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Page<Appointment> findAllByHealthUnitIdAndDateTimeBetween(long healthUnitId,
                                                           LocalDateTime startDateTime,
                                                           LocalDateTime endDateTime,
                                                           Pageable pageable);

    Page<Appointment> findAllAvailableByHealthUnitAndSpecialty(long healthUnitId, long specialtyId, Pageable pageable);

}
