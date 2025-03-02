package br.com.fiap.triage_service.infra.gateway.jpa;

import br.com.fiap.triage_service.domain.model.Triage;
import br.com.fiap.triage_service.infra.gateway.jpa.entity.TriageEntity;
import br.com.fiap.triage_service.infra.gateway.jpa.repository.TriageRepository;
import br.com.fiap.triage_service.helper.TriageHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class TriageJpaGatewayTestIT {

    @Autowired
    private TriageRepository triageRepository;

    private TriageEntity triageEntity;

    @BeforeEach
    void setUp() {
        triageEntity = TriageHelper.createTriageEntity(null, null);
        triageEntity = triageRepository.save(triageEntity);
    }

    @Test
    void shouldSaveTriage() {
        var triage = TriageHelper.createTriageEntity(null, null);
        UUID patientId = UUID.fromString("328bcfaa-83af-47cc-a732-f9e44e0d7600");
        triage.setPatientId(patientId);
        triage = triageRepository.save(triage);

        assertThat(triage.getId()).isNotNull();
        assertThat(triage.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void shouldFindAll() {
        var triages = triageRepository.findAll();

        assertThat(triages).hasSize(1);
        assertThat(triages.get(0).getId()).isEqualTo(triageEntity.getId());
    }

    @Test
    void shouldFindByPatientId() {
        List<TriageEntity> triages = triageRepository.findByPatientId(triageEntity.getPatientId());

        assertThat(triages).isNotEmpty();
        assertThat(triages.get(0).getPatientId()).isEqualTo(triageEntity.getPatientId());
    }
}
