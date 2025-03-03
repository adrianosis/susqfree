package br.com.susqfree.health_unit_management.infra.gateway.jpa;

import br.com.susqfree.health_unit_management.domain.gateway.HealthUnitGateway;
import br.com.susqfree.health_unit_management.domain.model.HealthUnit;
import br.com.susqfree.health_unit_management.infra.gateway.jpa.entity.HealthUnitEntity;
import br.com.susqfree.health_unit_management.infra.gateway.jpa.mapper.HealthUnitEntityMapper;
import br.com.susqfree.health_unit_management.infra.gateway.jpa.repository.HealthUnitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HealthUnitJpaGateway implements HealthUnitGateway {

    private final HealthUnitRepository repository;

    @Override
    public HealthUnit save(HealthUnit healthUnit) {
        HealthUnitEntity healthUnitEntity = HealthUnitEntityMapper.toEntity(healthUnit);

        healthUnitEntity = repository.save(healthUnitEntity);

        return HealthUnitEntityMapper.toDomain(healthUnitEntity);
    }

    public HealthUnit update(Long id, HealthUnit healthUnit) {
        HealthUnitEntity existingEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Health Unit not found"));

        BeanUtils.copyProperties(healthUnit, existingEntity, "id");

        existingEntity = repository.save(existingEntity);

        return HealthUnitEntityMapper.toDomain(existingEntity);
    }

    @Override
    public HealthUnit findById(Long id) {
        HealthUnitEntity healthUnitEntity = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Health Unit not found"));

        return HealthUnitEntityMapper.toDomain(healthUnitEntity);
    }

    @Override
    public List<HealthUnit> findAll() {
        return repository.findAll()
                .stream()
                .map(HealthUnitEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

}
