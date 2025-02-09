package br.com.susqfree.doctor_management.domain.gateway;

import br.com.susqfree.doctor_management.domain.model.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorGateway {

    Doctor save(Doctor doctor);

    Optional<Doctor> findById(Long id);

    List<Doctor> findBySpecialtyName(String specialtyName);

    void deleteById(Long id);
}
