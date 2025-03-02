package br.com.susqfree.schedule_management.infra.gateway.db.mongo;

import br.com.susqfree.schedule_management.domain.gateway.AppointmentGateway;
import br.com.susqfree.schedule_management.domain.model.Appointment;
import br.com.susqfree.schedule_management.infra.gateway.db.mongo.mapper.AppointmentDocumentMapper;
import br.com.susqfree.schedule_management.infra.gateway.db.mongo.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AppointmentMongoGateway implements AppointmentGateway {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentDocumentMapper mapper;

    @Override
    public Appointment save(Appointment appointment) {
        var document = mapper.toDocument(appointment);

        document = appointmentRepository.save(document);

        return mapper.toDomain(document);
    }

    @Override
    public List<Appointment> saveAll(List<Appointment> appointments) {
        var documents = appointments.stream().map(mapper::toDocument).toList();

        documents = appointmentRepository.saveAll(documents);

        return documents.stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Appointment> findById(UUID appointmentId) {
        var document = appointmentRepository.findById(appointmentId);

        return document.map(mapper::toDomain);
    }

    @Override
    public List<Appointment> findAllByPatientIdAndDateTimeBetween(UUID patientId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        var documents = appointmentRepository.findAllByPatientIdAndDateTimeBetween(patientId, startDateTime, endDateTime);

        return documents.stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Appointment> findAllByDoctorIdAndDateTimeBetween(long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        var documents = appointmentRepository.findAllByDoctorIdAndDateTimeBetween(doctorId, startDateTime, endDateTime);

        return documents.stream().map(mapper::toDomain).toList();
    }

    @Override
    public Page<Appointment> findAllByHealthUnitIdAndDateTimeBetween(long healthUnitId,
                                                                     LocalDateTime startDateTime,
                                                                     LocalDateTime endDateTime,
                                                                     Pageable pageable) {
        var documentsPage = appointmentRepository.findAllByHealthUnitIdAndDateTimeBetween(healthUnitId, startDateTime, endDateTime, pageable);

        return documentsPage.map(mapper::toDomain);
    }

    @Override
    public Page<Appointment> findAllAvailableByHealthUnitAndSpecialty(long healthUnitId, long specialtyId, Pageable pageable) {
        var documentsPage = appointmentRepository.findAllByHealthUnitIdAndSpecialtyIdOrderByDateTime(healthUnitId, specialtyId, pageable);

        return documentsPage.map(mapper::toDomain);
    }

}
