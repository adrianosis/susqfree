package br.com.fiap.triage_service.infra.gateway.jpa;

import br.com.fiap.triage_service.domain.gateway.TriageGateway;
import br.com.fiap.triage_service.domain.model.Triage;
import br.com.fiap.triage_service.infra.gateway.jpa.entity.TriageEntity;
import br.com.fiap.triage_service.infra.gateway.jpa.mapper.TriageEntityMapper;
import br.com.fiap.triage_service.infra.gateway.jpa.repository.TriageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TriageJpaGateway implements TriageGateway {

    private final TriageRepository repository;

    @Override
    public Triage create(Triage triage) {
        TriageEntity triageEntity = TriageEntityMapper.toEntity(triage);
        triageEntity = repository.save(triageEntity);
        return TriageEntityMapper.toDomain(triageEntity);
    }

    @Override
    public Page<Triage> findAll(Pageable pageable) {
        Page<TriageEntity> triageEntities = repository.findAll(pageable);
        List<Triage> triages =  triageEntities.stream()
                .map(TriageEntityMapper::toDomain)
                .collect(Collectors.toList());
        return new PageImpl<>(triages, pageable, triageEntities.getTotalElements());
    }

    @Override
    public List<Triage> findByPatientId(UUID id) {
        List<TriageEntity> triageEntities = repository.findByPatientId(id);
        return triageEntities.stream()
                .map(TriageEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

}
