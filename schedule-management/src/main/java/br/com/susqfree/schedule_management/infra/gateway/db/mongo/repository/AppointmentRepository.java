package br.com.susqfree.schedule_management.infra.gateway.db.mongo.repository;

import br.com.susqfree.schedule_management.infra.gateway.db.mongo.document.AppointmentDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends MongoRepository<AppointmentDocument, UUID> {

    @Query("{ $and: [ { 'patient.id': { $gte: ?0 } }, { 'dateTime': { $gte: ?1, $lte: ?2 } }] }")
    List<AppointmentDocument> findAllByPatientIdAndDateTimeBetween(UUID patientId,
                                                                   LocalDateTime startDateTime,
                                                                   LocalDateTime endDateTime);

    @Query("{ $and: [ { 'doctor.id': { $gte: ?0 } }, { 'dateTime': { $gte: ?1, $lte: ?2 } }] }")
    List<AppointmentDocument> findAllByDoctorIdAndDateTimeBetween(long doctorId,
                                                                  LocalDateTime startDateTime,
                                                                  LocalDateTime endDateTime);

    @Query("{ $and: [ { 'healthUnit.id': { $gte: ?0 } }, { 'dateTime': { $gte: ?1, $lte: ?2 } }] }")
    Page<AppointmentDocument> findAllByHealthUnitIdAndDateTimeBetween(long healthUnitId,
                                                                      LocalDateTime startDateTime,
                                                                      LocalDateTime endDateTime,
                                                                      Pageable pageable);

    @Query("{ $and: [{ 'status': 'AVAILABLE' }, { 'healthUnit.id': ?0 }, { 'specialty.id': ?1 }] }")
    Page<AppointmentDocument> findAllByHealthUnitIdAndSpecialtyIdOrderByDateTime(long healthUnitId,
                                                                                 long specialtyId,
                                                                                 Pageable pageable);

}
