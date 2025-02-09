package br.com.susqfree.doctor_management.infra.gateway.jpa;

import br.com.susqfree.doctor_management.domain.gateway.SpecialtyGateway;
import br.com.susqfree.doctor_management.domain.model.Specialty;
import br.com.susqfree.doctor_management.infra.gateway.jpa.entity.SpecialtyEntity;
import br.com.susqfree.doctor_management.infra.gateway.jpa.mapper.SpecialtyEntityMapper;
import br.com.susqfree.doctor_management.infra.gateway.jpa.repository.SpecialtyRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SpecialtyJpaGateway implements SpecialtyGateway {

    private final SpecialtyRepository specialtyRepository;

    public SpecialtyJpaGateway(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public Specialty save(Specialty specialty) {
        SpecialtyEntity savedEntity = specialtyRepository.save(SpecialtyEntityMapper.toEntity(specialty));
        return SpecialtyEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Specialty> findById(Long id) {
        return specialtyRepository.findById(id)
                .map(SpecialtyEntityMapper::toDomain);
    }

    @Override
    public List<Specialty> findAll() {
        return specialtyRepository.findAll().stream()
                .map(SpecialtyEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        specialtyRepository.deleteById(id);
    }
}
