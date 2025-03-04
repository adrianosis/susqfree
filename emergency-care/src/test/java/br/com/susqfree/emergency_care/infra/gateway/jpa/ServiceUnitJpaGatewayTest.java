package br.com.susqfree.emergency_care.infra.gateway.jpa;

import br.com.susqfree.emergency_care.domain.model.ServiceUnit;
import br.com.susqfree.emergency_care.infra.gateway.jpa.entity.ServiceUnitEntity;
import br.com.susqfree.emergency_care.infra.gateway.jpa.mapper.ServiceUnitEntityMapper;
import br.com.susqfree.emergency_care.infra.gateway.jpa.repository.ServiceUnitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServiceUnitJpaGatewayTest {

    private ServiceUnitJpaGateway serviceUnitJpaGateway;

    @Mock
    private ServiceUnitRepository repository;

    @Mock
    private ServiceUnitEntityMapper mapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        serviceUnitJpaGateway = new ServiceUnitJpaGateway(repository, mapper);
    }

    @Test
    @DisplayName("Deve salvar uma unidade de serviço")
    void shouldSaveServiceUnit() {
        var serviceUnit = new ServiceUnit(1L, "Emergency Unit", 100, 10L);
        var entity = new ServiceUnitEntity(1L, "Emergency Unit", 100, 10L);

        when(mapper.toEntity(any(ServiceUnit.class))).thenReturn(entity);
        when(repository.save(any(ServiceUnitEntity.class))).thenReturn(entity);
        when(mapper.toDomain(any(ServiceUnitEntity.class))).thenReturn(serviceUnit);

        var savedServiceUnit = serviceUnitJpaGateway.save(serviceUnit);

        verify(repository, times(1)).save(any(ServiceUnitEntity.class));
        assertThat(savedServiceUnit).isNotNull();
        assertThat(savedServiceUnit.getId()).isEqualTo(serviceUnit.getId());
        assertThat(savedServiceUnit.getServiceType()).isEqualTo(serviceUnit.getServiceType());
        assertThat(savedServiceUnit.getCapacity()).isEqualTo(serviceUnit.getCapacity());
        assertThat(savedServiceUnit.getUnitId()).isEqualTo(serviceUnit.getUnitId());
    }

    @Test
    @DisplayName("Deve encontrar uma unidade de serviço pelo ID")
    void shouldFindServiceUnitById() {
        var serviceUnit = new ServiceUnit(1L, "Emergency Unit", 100, 10L);
        var entity = new ServiceUnitEntity(1L, "Emergency Unit", 100, 10L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(serviceUnit);

        var foundServiceUnit = serviceUnitJpaGateway.findById(1L);

        verify(repository, times(1)).findById(1L);
        assertThat(foundServiceUnit).isPresent();
        assertThat(foundServiceUnit.get().getId()).isEqualTo(serviceUnit.getId());
        assertThat(foundServiceUnit.get().getServiceType()).isEqualTo(serviceUnit.getServiceType());
    }

    @Test
    @DisplayName("Deve retornar vazio quando não encontrar uma unidade de serviço pelo ID")
    void shouldReturnEmptyWhenServiceUnitNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        var foundServiceUnit = serviceUnitJpaGateway.findById(99L);

        verify(repository, times(1)).findById(99L);
        assertThat(foundServiceUnit).isEmpty();
    }

    @Test
    @DisplayName("Deve listar todas as unidades de serviço")
    void shouldFindAllServiceUnits() {
        var serviceUnit1 = new ServiceUnit(1L, "Emergency Unit", 100, 10L);
        var serviceUnit2 = new ServiceUnit(2L, "General Clinic", 200, 20L);

        var entity1 = new ServiceUnitEntity(1L, "Emergency Unit", 100, 10L);
        var entity2 = new ServiceUnitEntity(2L, "General Clinic", 200, 20L);

        when(repository.findAll()).thenReturn(List.of(entity1, entity2));
        when(mapper.toDomain(entity1)).thenReturn(serviceUnit1);
        when(mapper.toDomain(entity2)).thenReturn(serviceUnit2);

        var serviceUnits = serviceUnitJpaGateway.findAll();

        verify(repository, times(1)).findAll();
        assertThat(serviceUnits).hasSize(2);
        assertThat(serviceUnits.get(0).getId()).isEqualTo(serviceUnit1.getId());
        assertThat(serviceUnits.get(1).getId()).isEqualTo(serviceUnit2.getId());
    }
}
