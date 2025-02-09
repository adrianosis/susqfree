package br.com.susqfree.doctor_management.infra.gateway.jpa.repository;

import br.com.susqfree.doctor_management.infra.gateway.jpa.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

    List<DoctorEntity> findBySpecialties_Name(String specialtyName);
}
