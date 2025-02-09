package br.com.susqfree.doctor_management.domain.gateway;

import br.com.susqfree.doctor_management.domain.model.Specialty;

import java.util.List;
import java.util.Optional;

public interface SpecialtyGateway {

    Specialty save(Specialty specialty);

    Optional<Specialty> findById(Long id);

    List<Specialty> findAll();

    void deleteById(Long id);
}
