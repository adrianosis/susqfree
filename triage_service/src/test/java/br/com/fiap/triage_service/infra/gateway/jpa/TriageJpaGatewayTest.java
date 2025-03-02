package br.com.fiap.triage_service.infra.gateway.jpa;

import br.com.fiap.triage_service.domain.model.Triage;
import br.com.fiap.triage_service.helper.TriageHelper;
import br.com.fiap.triage_service.infra.gateway.jpa.entity.PriorityCode;
import br.com.fiap.triage_service.infra.gateway.jpa.entity.TriageEntity;
import br.com.fiap.triage_service.infra.gateway.jpa.mapper.TriageEntityMapper;
import br.com.fiap.triage_service.infra.gateway.jpa.repository.TriageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TriageJpaGatewayTest {

    private TriageJpaGateway triageJpaGateway;

    @Mock
    private TriageRepository repository;

    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        triageJpaGateway = new TriageJpaGateway(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (openMocks != null) {
            openMocks.close();
        }
    }

    @Test
    void shouldCreateTriage() {
        Triage triage = TriageHelper.createTriage(null,null);
        TriageEntity triageEntity =  TriageHelper.createTriageEntity(1, PriorityCode.R);
        when(repository.save(any(TriageEntity.class))).thenReturn(triageEntity);

        Triage result = triageJpaGateway.create(triage);

        verify(repository, times(1)).save(any(TriageEntity.class));
        assertThat(result).isNotNull();
    }

    @Test
    void shouldFindAllTriages() {
        TriageEntity triageEntity = TriageHelper.createTriageEntity(1, PriorityCode.R);
        TriageEntity triageEntity2 = TriageHelper.createTriageEntity(2, PriorityCode.R);

        Pageable pageable = Pageable.unpaged();
        Page<TriageEntity> triageEntityPage = new PageImpl<>(
                List.of(triageEntity, triageEntity2));
        when(repository.findAll(pageable)).thenReturn(triageEntityPage);

        Page<Triage> result = triageJpaGateway.findAll(pageable);

        assertThat(result)
                .hasSize(2)
                .isNotEmpty();

        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    void shouldFindTriageByPatientId() {
        UUID patientId = UUID.fromString("328bcfaa-83af-47cc-a732-f9e44e0d7600");

        TriageEntity triageEntity = TriageHelper.createTriageEntity(1, PriorityCode.R);
        when(repository.findByPatientId(patientId)).thenReturn(List.of(triageEntity));

        List<Triage> result = triageJpaGateway.findByPatientId(patientId);

        // Assert
        verify(repository, times(1)).findByPatientId(patientId);
        assertThat(result).isNotEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenNoTriageFoundByPatientId() {
        UUID patientId = UUID.fromString("328bcfaa-83af-47cc-a732-f9e44e0d7600");

        when(repository.findByPatientId(patientId)).thenReturn(List.of());

        List<Triage> result = triageJpaGateway.findByPatientId(patientId);

        verify(repository, times(1)).findByPatientId(patientId);
        assertThat(result).isEmpty();
    }

}
