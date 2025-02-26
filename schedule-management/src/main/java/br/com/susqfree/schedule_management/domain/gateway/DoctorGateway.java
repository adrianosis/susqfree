package br.com.susqfree.schedule_management.domain.gateway;

import br.com.susqfree.schedule_management.domain.model.Doctor;

import java.util.Optional;

public interface DoctorGateway {

    Optional<Doctor> findById(long doctorId);

}
