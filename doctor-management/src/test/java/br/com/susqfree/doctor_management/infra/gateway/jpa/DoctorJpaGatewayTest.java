package br.com.susqfree.doctor_management.infra.gateway.jpa;

import br.com.susqfree.doctor_management.domain.model.Doctor;
import br.com.susqfree.doctor_management.domain.model.Specialty;
import br.com.susqfree.doctor_management.infra.gateway.jpa.entity.DoctorEntity;
import br.com.susqfree.doctor_management.infra.gateway.jpa.entity.SpecialtyEntity;
import br.com.susqfree.doctor_management.infra.gateway.jpa.repository.DoctorRepository;
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
class DoctorJpaGatewayTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    private DoctorJpaGateway doctorGateway;

    @BeforeEach
    void setUp() {
        doctorGateway = new DoctorJpaGateway(doctorRepository);
    }

    @Test
    void shouldSaveDoctorSuccessfully() {
        SpecialtyEntity specialtyEntity = specialtyRepository.save(new SpecialtyEntity(null, "Cardiology", "Heart specialist"));
        Doctor doctor = new Doctor(null, "Dr. Carlos Silva", "123456-SP", "(11) 91234-5678", "carlos.silva@exemplo.com", List.of(new Specialty(specialtyEntity.getId(), specialtyEntity.getName(), specialtyEntity.getDescription())));

        Doctor savedDoctor = doctorGateway.save(doctor);

        assertThat(savedDoctor.id()).isNotNull();
        assertThat(savedDoctor.name()).isEqualTo(doctor.name());
        assertThat(savedDoctor.specialties()).hasSize(1);
        assertThat(savedDoctor.specialties().get(0).name()).isEqualTo("Cardiology");
    }

    @Test
    void shouldFindDoctorByIdSuccessfully() {
        SpecialtyEntity specialtyEntity = specialtyRepository.save(new SpecialtyEntity(null, "Neurology", "Nervous system specialist"));
        DoctorEntity entity = new DoctorEntity(null, "Dr. Ana Souza", "654321-SP", "(21) 99876-5432", "ana.souza@exemplo.com", List.of(specialtyEntity));
        DoctorEntity savedEntity = doctorRepository.save(entity);

        Optional<Doctor> doctorOptional = doctorGateway.findById(savedEntity.getId());

        assertThat(doctorOptional).isPresent();
        Doctor doctor = doctorOptional.get();
        assertThat(doctor.id()).isEqualTo(savedEntity.getId());
        assertThat(doctor.name()).isEqualTo(savedEntity.getName());
        assertThat(doctor.specialties()).hasSize(1);
        assertThat(doctor.specialties().get(0).name()).isEqualTo("Neurology");
    }

    @Test
    void shouldFindDoctorsBySpecialtyNameSuccessfully() {
        SpecialtyEntity specialtyEntity = specialtyRepository.save(new SpecialtyEntity(null, "Orthopedics", "Bone specialist"));
        DoctorEntity doctorEntity1 = new DoctorEntity(null, "Dr. João Pereira", "321654-SP", "(11) 92345-6789", "joao.pereira@exemplo.com", List.of(specialtyEntity));
        DoctorEntity doctorEntity2 = new DoctorEntity(null, "Dr. Maria Silva", "987654-SP", "(21) 98765-4321", "maria.silva@exemplo.com", List.of(specialtyEntity));
        doctorRepository.save(doctorEntity1);
        doctorRepository.save(doctorEntity2);

        List<Doctor> doctors = doctorGateway.findBySpecialtyName("Orthopedics");

        assertThat(doctors).hasSize(2);
        assertThat(doctors).extracting(Doctor::name).containsExactlyInAnyOrder("Dr. João Pereira", "Dr. Maria Silva");
    }

    @Test
    void shouldDeleteDoctorByIdSuccessfully() {
        DoctorEntity entity = new DoctorEntity(null, "Dr. Lucas Nascimento", "852369-SP", "(11) 91234-5678", "lucas.nascimento@exemplo.com", List.of());
        DoctorEntity savedEntity = doctorRepository.save(entity);

        doctorGateway.deleteById(savedEntity.getId());

        Optional<DoctorEntity> deletedEntity = doctorRepository.findById(savedEntity.getId());
        assertThat(deletedEntity).isEmpty();
    }
}
