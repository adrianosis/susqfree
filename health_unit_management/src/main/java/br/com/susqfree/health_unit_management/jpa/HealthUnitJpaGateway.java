package br.com.susqfree.health_unit_management.jpa;

import br.com.susqfree.health_unit_management.domain.gateway.HealthUnitGateway;
import br.com.susqfree.health_unit_management.domain.model.HealthUnit;
import br.com.susqfree.health_unit_management.jpa.entity.HealthUnitEntity;
import br.com.susqfree.health_unit_management.jpa.mapper.HealthUnitEntityMapper;
import br.com.susqfree.health_unit_management.jpa.repository.HealthUnitRepository;
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
                .orElseThrow(() -> new RuntimeException("Health Unit not found"));

        BeanUtils.copyProperties(healthUnit, existingEntity, "id");

        existingEntity = repository.save(existingEntity);

        return HealthUnitEntityMapper.toDomain(existingEntity);
    }

    @Override
    public HealthUnit findById(Long id) {
        HealthUnitEntity healthUnitEntity = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Health Unit not found"));

        return HealthUnitEntityMapper.toDomain(healthUnitEntity);
    }

    @Override
    public void delete(Long id) {
        HealthUnitEntity healthUnitEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Health Unit not found"));
        repository.delete(healthUnitEntity);
    }

    @Override
    public List<HealthUnit> findAll() {
        return repository.findAll()
                .stream()
                .map(HealthUnitEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

}
