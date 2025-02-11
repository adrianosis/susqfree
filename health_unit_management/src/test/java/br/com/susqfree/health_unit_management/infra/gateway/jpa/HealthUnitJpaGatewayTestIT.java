package br.com.susqfree.health_unit_management.infra.gateway.jpa;

import br.com.susqfree.health_unit_management.infra.gateway.jpa.entity.HealthUnitEntity;
import br.com.susqfree.health_unit_management.infra.gateway.jpa.repository.HealthUnitRepository;
import br.com.susqfree.health_unit_management.utils.HealthUnitHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class HealthUnitJpaGatewayTestIT {

    @Autowired
    private HealthUnitRepository healthUnitRepository;

    private HealthUnitEntity healthUnitEntity;

    @BeforeEach
    void setUp() {
        healthUnitEntity = HealthUnitHelper.createHealthUnitEntity(null);
        healthUnitEntity = healthUnitRepository.save(healthUnitEntity);
    }


    @Test
    void shouldSaveHealthUnit() {
        var healthUnit = HealthUnitHelper.createHealthUnitEntity(null);
        healthUnit.setName("UPA Niteroi");
        healthUnit = healthUnitRepository.save(healthUnit);

        assertThat(healthUnit.getId()).isNotNull();
        assertThat(healthUnit.getName()).isEqualTo("UPA Niteroi");
        assertThat(healthUnit.getState()).isEqualTo("RJ");
    }

    @Test
    void shouldUpdateHealthUnit() {
        healthUnitEntity.setCity("Sao Paulo");
        healthUnitEntity.setState("SP");
        healthUnitEntity = healthUnitRepository.save(healthUnitEntity);
        assertThat(healthUnitEntity.getCity()).isEqualTo("Sao Paulo");
        assertThat(healthUnitEntity.getName()).isEqualTo("Unidade de Saude Central");
        assertThat(healthUnitEntity.getState()).isEqualTo("SP");
    }

    @Test
    void shouldFindById() {
        var healthUnit = healthUnitRepository.findById(healthUnitEntity.getId());
        assertThat(healthUnit.isPresent()).isTrue();
        assertThat(healthUnit.get().getId()).isEqualTo(healthUnitEntity.getId());
        assertThat(healthUnit.get().getName()).isEqualTo("Unidade de Saude Central");
        assertThat(healthUnit.get().getState()).isEqualTo("RJ");
    }

    @Test
    void shouldFindAll() {
        var healthUnits = healthUnitRepository.findAll();
        assertThat(healthUnits).hasSize(1);
        assertThat(healthUnits.get(0).getId()).isEqualTo(healthUnitEntity.getId());
        assertThat(healthUnits.get(0).getName()).isEqualTo("Unidade de Saude Central");
        assertThat(healthUnits.get(0).getState()).isEqualTo("RJ");
    }
}