package br.com.susqfree.emergency_care.infra.gateway.jpa;

import br.com.susqfree.emergency_care.domain.gateway.ServiceUnitGateway;
import br.com.susqfree.emergency_care.domain.model.ServiceUnit;
import br.com.susqfree.emergency_care.infra.gateway.jpa.mapper.ServiceUnitEntityMapper;
import br.com.susqfree.emergency_care.infra.gateway.jpa.repository.ServiceUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ServiceUnitJpaGateway implements ServiceUnitGateway {

    private final ServiceUnitRepository repository;
    private final ServiceUnitEntityMapper mapper;

    public ServiceUnitJpaGateway(ServiceUnitRepository repository, ServiceUnitEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ServiceUnit save(ServiceUnit serviceUnit) {
        return mapper.toDomain(repository.save(mapper.toEntity(serviceUnit)));
    }

    @Override
    public Optional<ServiceUnit> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<ServiceUnit> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
