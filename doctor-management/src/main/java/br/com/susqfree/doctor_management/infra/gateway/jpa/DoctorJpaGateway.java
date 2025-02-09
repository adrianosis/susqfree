package br.com.susqfree.doctor_management.infra.gateway.jpa;

import br.com.susqfree.doctor_management.domain.gateway.DoctorGateway;
import br.com.susqfree.doctor_management.domain.model.Doctor;
import br.com.susqfree.doctor_management.infra.gateway.jpa.entity.DoctorEntity;
import br.com.susqfree.doctor_management.infra.gateway.jpa.mapper.DoctorEntityMapper;
import br.com.susqfree.doctor_management.infra.gateway.jpa.repository.DoctorRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DoctorJpaGateway implements DoctorGateway {

    private final DoctorRepository doctorRepository;

    public DoctorJpaGateway(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor save(Doctor doctor) {
        DoctorEntity savedEntity = doctorRepository.save(DoctorEntityMapper.toEntity(doctor));
        return DoctorEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        return doctorRepository.findById(id)
                .map(DoctorEntityMapper::toDomain);
    }

    @Override
    public List<Doctor> findBySpecialtyName(String specialtyName) {
        return doctorRepository.findBySpecialties_Name(specialtyName).stream()
                .map(DoctorEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        doctorRepository.deleteById(id);
    }
}
