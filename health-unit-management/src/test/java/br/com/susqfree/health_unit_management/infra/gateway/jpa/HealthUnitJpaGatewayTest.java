package br.com.susqfree.health_unit_management.infra.gateway.jpa;

import br.com.susqfree.health_unit_management.domain.model.HealthUnit;
import br.com.susqfree.health_unit_management.infra.gateway.jpa.entity.HealthUnitEntity;
import br.com.susqfree.health_unit_management.infra.gateway.jpa.repository.HealthUnitRepository;
import br.com.susqfree.health_unit_management.utils.HealthUnitHelper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HealthUnitJpaGatewayTest {

    private HealthUnitJpaGateway healthUnitJpaGateway;

    @Mock
    private HealthUnitRepository repository;

    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        healthUnitJpaGateway = new HealthUnitJpaGateway(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (openMocks != null) {
            openMocks.close();
        }
    }

    @Test
    void shouldSaveHealthUnit() {
        // Arrange
        HealthUnit healthUnit = HealthUnitHelper.createHealthUnit(null);
        HealthUnitEntity healthUnitEntity = HealthUnitHelper.createHealthUnitEntity(1L);
        when(repository.save(any(HealthUnitEntity.class))).thenReturn(healthUnitEntity);

        // Act
        HealthUnit result = healthUnitJpaGateway.save(healthUnit);

        // Assert
        verify(repository, times(1)).save(any(HealthUnitEntity.class));
        assertThat(result).isNotNull();
    }

    @Test
    void shouldUpdateHealthUnit() {
        // Arrange
        Long id = 1L;
        HealthUnit healthUnit = HealthUnitHelper.createHealthUnit(null);
        HealthUnitEntity existingEntity = HealthUnitHelper.createHealthUnitEntity(id);

        when(repository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(repository.save(any(HealthUnitEntity.class))).thenReturn(existingEntity);

        // Act
        HealthUnit result = healthUnitJpaGateway.update(id, healthUnit);

        // Assert
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(existingEntity);
        assertThat(result).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentHealthUnit() {
        // Arrange
        Long id = 1L;
        HealthUnit healthUnit = HealthUnitHelper.createHealthUnit(id);
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> healthUnitJpaGateway.update(id, healthUnit));
        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void shouldFindHealthUnitById() {
        // Arrange
        Long id = 1L;
        HealthUnitEntity healthUnitEntity = HealthUnitHelper.createHealthUnitEntity(id);
        when(repository.findById(id)).thenReturn(Optional.of(healthUnitEntity));

        // Act
        HealthUnit result = healthUnitJpaGateway.findById(id);

        // Assert
        verify(repository, times(1)).findById(id);
        assertThat(result).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenHealthUnitNotFoundById() {
        // Arrange
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> healthUnitJpaGateway.findById(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    void shouldFindAllHealthUnitsByCityAndState() {
        // Arrange
        HealthUnitEntity healthUnitEntity = HealthUnitHelper.createHealthUnitEntity(1L);
        when(repository.findAllByCityAndState(anyString(), anyString())).thenReturn(List.of(healthUnitEntity));

        // Act
        List<HealthUnit> result = healthUnitJpaGateway.findAllByCityAndState(anyString(), anyString());

        // Assert
        verify(repository, times(1)).findAllByCityAndState(anyString(), anyString());
        assertThat(result).isNotEmpty();
    }
}
