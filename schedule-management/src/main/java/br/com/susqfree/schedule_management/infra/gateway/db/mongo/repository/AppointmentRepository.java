package br.com.susqfree.schedule_management.infra.gateway.db.mongo.repository;

import br.com.susqfree.schedule_management.infra.gateway.db.mongo.document.AppointmentDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends MongoRepository<AppointmentDocument, UUID> {

    List<AppointmentDocument> findAllByPatientIdAndDateTimeBetween(UUID patientId,
                                                                   LocalDateTime startDateTime,
                                                                   LocalDateTime endDateTime);

    List<AppointmentDocument> findAllByDoctorIdAndDateTimeBetween(long doctorId,
                                                                  LocalDateTime startDateTime,
                                                                  LocalDateTime endDateTime);

    Page<AppointmentDocument> findAllByHealthUnitIdAndDateTimeBetween(long healthUnitId,
                                                                      LocalDateTime startDateTime,
                                                                      LocalDateTime endDateTime,
                                                                      Pageable pageable);

    Page<AppointmentDocument> findByHealthUnitIdAndSpecialtyIdOrderByDateTime(long healthUnitId,
                                                                              long specialtyId,
                                                                              Pageable pageable);
}
