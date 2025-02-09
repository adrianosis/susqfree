package br.com.susqfree.doctor_management.infra.gateway.jpa;

import br.com.susqfree.doctor_management.domain.model.Specialty;
import br.com.susqfree.doctor_management.infra.gateway.jpa.entity.SpecialtyEntity;
import br.com.susqfree.doctor_management.infra.gateway.jpa.repository.SpecialtyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class SpecialtyJpaGatewayTest {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    private SpecialtyJpaGateway specialtyGateway;

    @BeforeEach
    void setUp() {
        specialtyGateway = new SpecialtyJpaGateway(specialtyRepository);
    }

    @Test
    void shouldSaveSpecialtySuccessfully() {
        Specialty specialty = new Specialty(null, "Cardiology", "Specialty focused on heart diseases");

        Specialty savedSpecialty = specialtyGateway.save(specialty);

        assertThat(savedSpecialty.id()).isNotNull();
        assertThat(savedSpecialty.name()).isEqualTo(specialty.name());
        assertThat(savedSpecialty.description()).isEqualTo(specialty.description());
    }

    @Test
    void shouldFindSpecialtyByIdSuccessfully() {
        SpecialtyEntity entity = new SpecialtyEntity(null, "Neurology", "Specialty focused on the nervous system");
        SpecialtyEntity savedEntity = specialtyRepository.save(entity);

        Optional<Specialty> specialtyOptional = specialtyGateway.findById(savedEntity.getId());

        assertThat(specialtyOptional).isPresent();
        Specialty specialty = specialtyOptional.get();
        assertThat(specialty.id()).isEqualTo(savedEntity.getId());
        assertThat(specialty.name()).isEqualTo(savedEntity.getName());
        assertThat(specialty.description()).isEqualTo(savedEntity.getDescription());
    }

    @Test
    void shouldFindAllSpecialtiesSuccessfully() {
        specialtyRepository.save(new SpecialtyEntity(null, "Orthopedics", "Focuses on the musculoskeletal system"));
        specialtyRepository.save(new SpecialtyEntity(null, "Pediatrics", "Focuses on the health of children"));

        List<Specialty> specialties = specialtyGateway.findAll();

        assertThat(specialties).hasSize(2);
        assertThat(specialties).extracting(Specialty::name)
                .containsExactlyInAnyOrder("Orthopedics", "Pediatrics");
    }

    @Test
    void shouldDeleteSpecialtyByIdSuccessfully() {
        SpecialtyEntity entity = new SpecialtyEntity(null, "Dermatology", "Specialty focused on skin diseases");
        SpecialtyEntity savedEntity = specialtyRepository.save(entity);

        specialtyGateway.deleteById(savedEntity.getId());

        Optional<SpecialtyEntity> deletedEntity = specialtyRepository.findById(savedEntity.getId());
        assertThat(deletedEntity).isEmpty();
    }
}
