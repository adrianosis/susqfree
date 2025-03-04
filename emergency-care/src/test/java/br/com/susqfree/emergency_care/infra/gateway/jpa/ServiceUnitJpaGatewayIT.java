package br.com.susqfree.emergency_care.infra.gateway.jpa;

import br.com.susqfree.emergency_care.domain.model.ServiceUnit;
import br.com.susqfree.emergency_care.infra.gateway.jpa.entity.ServiceUnitEntity;
import br.com.susqfree.emergency_care.infra.gateway.jpa.mapper.ServiceUnitEntityMapper;
import br.com.susqfree.emergency_care.infra.gateway.jpa.repository.ServiceUnitRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(ServiceUnitEntityMapper.class)
class ServiceUnitJpaGatewayIT {

    @Autowired
    private ServiceUnitRepository serviceUnitRepository;

    private ServiceUnitJpaGateway serviceUnitJpaGateway;

    @Autowired
    private ServiceUnitEntityMapper serviceUnitEntityMapper;

    @BeforeEach
    void setUp() {
        serviceUnitJpaGateway = new ServiceUnitJpaGateway(serviceUnitRepository, serviceUnitEntityMapper);
    }

    @Test
    void shouldSaveServiceUnitSuccessfully() {
        ServiceUnit serviceUnit = new ServiceUnit(null, "Emergency Unit", 100, 10L);

        ServiceUnit savedServiceUnit = serviceUnitJpaGateway.save(serviceUnit);

        assertThat(savedServiceUnit).isNotNull();
        assertThat(savedServiceUnit.getId()).isNotNull();
        assertThat(savedServiceUnit.getServiceType()).isEqualTo(serviceUnit.getServiceType());
        assertThat(savedServiceUnit.getCapacity()).isEqualTo(serviceUnit.getCapacity());
        assertThat(savedServiceUnit.getUnitId()).isEqualTo(serviceUnit.getUnitId());
    }

    @Test
    @Transactional
    void shouldFindServiceUnitByIdSuccessfully() {
        ServiceUnitEntity entity = serviceUnitRepository.save(new ServiceUnitEntity(null, "Emergency Unit", 100, 10L));
        serviceUnitRepository.flush();

        Optional<ServiceUnit> foundServiceUnit = serviceUnitJpaGateway.findById(entity.getId());

        assertThat(foundServiceUnit).isPresent();
    }

    @Test
    void shouldFindAllServiceUnitsSuccessfully() {
        serviceUnitRepository.saveAll(List.of(
                new ServiceUnitEntity(null, "Emergency Unit", 100, 10L),
                new ServiceUnitEntity(null, "ICU", 50, 5L)
        ));

        List<ServiceUnit> serviceUnits = serviceUnitJpaGateway.findAll();

        assertThat(serviceUnits).hasSize(2);
    }
}
