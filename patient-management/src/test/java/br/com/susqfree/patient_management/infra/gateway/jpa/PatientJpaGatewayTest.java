package br.com.susqfree.patient_management.infra.gateway.jpa;

import br.com.susqfree.patient_management.domain.model.Patient;
import br.com.susqfree.patient_management.infra.gateway.jpa.entity.AddressEntity;
import br.com.susqfree.patient_management.infra.gateway.jpa.entity.PatientEntity;
import br.com.susqfree.patient_management.infra.gateway.jpa.mapper.PatientEntityMapper;
import br.com.susqfree.patient_management.infra.gateway.jpa.repository.PatientRepository;
import br.com.susqfree.patient_management.utils.PatientHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PatientJpaGatewayTest {

    private PatientJpaGateway patientGateway;

    @Mock
    private PatientRepository patientRepository;
    AutoCloseable openMocks;


    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        patientGateway = new PatientJpaGateway(patientRepository, new PatientEntityMapper());
    }

    @Test
    public void shouldCreatePatient() {
        // Arrange
        UUID patientId = UUID.randomUUID();
        Patient patient = PatientHelper.createPatient(patientId);

        when(patientRepository.save(any(PatientEntity.class))).thenAnswer(returnsFirstArg());

        // Act
        var savedPatient = patientGateway.save(patient);

        // Assert
        verify(patientRepository, times(1)).save(any(PatientEntity.class));

        assertThat(savedPatient).isInstanceOf(Patient.class).isNotNull();
        assertThat(savedPatient.getName()).isEqualTo(patient.getName());
        assertThat(savedPatient.getGender()).isEqualTo(patient.getGender());
        assertThat(savedPatient.getBirthDate()).isEqualTo(patient.getBirthDate());
        assertThat(savedPatient.getCpf()).isEqualTo(patient.getCpf());
        assertThat(savedPatient.getSusNumber()).isEqualTo(patient.getSusNumber());
        assertThat(savedPatient.getPhoneNumber()).isEqualTo(patient.getPhoneNumber());
        assertThat(savedPatient.getMail()).isEqualTo(patient.getMail());
        assertThat(savedPatient.getAddress().getStreet()).isEqualTo(patient.getAddress().getStreet());
        assertThat(savedPatient.getAddress().getNumber()).isEqualTo(patient.getAddress().getNumber());
        assertThat(savedPatient.getAddress().getDistrict()).isEqualTo(patient.getAddress().getDistrict());
        assertThat(savedPatient.getAddress().getCity()).isEqualTo(patient.getAddress().getCity());
        assertThat(savedPatient.getAddress().getState()).isEqualTo(patient.getAddress().getState());
        assertThat(savedPatient.getAddress().getPostalCode()).isEqualTo(patient.getAddress().getPostalCode());
    }

    @Test
    public void shouldFindPatientById() {
        // Arrange
        UUID patientId = UUID.randomUUID();
        AddressEntity address = AddressEntity.builder()
                .street("Av. Paulista")
                .number("1400")
                .district("Bela Vista")
                .city("São Paulo")
                .state("SP")
                .postalCode("12345678")
                .build();
        PatientEntity patientEntity = PatientEntity.builder()
                .id(patientId)
                .name("Julia")
                .gender("F")
                .birthDate(LocalDate.of(2005, 6, 24))
                .cpf("11133355500")
                .susNumber("123412341234")
                .phoneNumber("11999995555")
                .mail("julia@test.com")
                .address(address)
                .build();

        when(patientRepository.findById(any(UUID.class))).thenReturn(Optional.of(patientEntity));

        // Act
        var optionalFoundPatient = patientGateway.findById(patientId);

        // Assert
        verify(patientRepository, times(1)).findById(any(UUID.class));

        assertThat(optionalFoundPatient).isNotEmpty();
        optionalFoundPatient.ifPresent(foundPatient -> {
            assertThat(foundPatient.getName()).isEqualTo(patientEntity.getName());
            assertThat(foundPatient.getGender()).isEqualTo(patientEntity.getGender());
            assertThat(foundPatient.getBirthDate()).isEqualTo(patientEntity.getBirthDate());
            assertThat(foundPatient.getCpf()).isEqualTo(patientEntity.getCpf());
            assertThat(foundPatient.getSusNumber()).isEqualTo(patientEntity.getSusNumber());
            assertThat(foundPatient.getPhoneNumber()).isEqualTo(patientEntity.getPhoneNumber());
            assertThat(foundPatient.getMail()).isEqualTo(patientEntity.getMail());
            assertThat(foundPatient.getAddress().getStreet()).isEqualTo(patientEntity.getAddress().getStreet());
            assertThat(foundPatient.getAddress().getNumber()).isEqualTo(patientEntity.getAddress().getNumber());
            assertThat(foundPatient.getAddress().getDistrict()).isEqualTo(patientEntity.getAddress().getDistrict());
            assertThat(foundPatient.getAddress().getCity()).isEqualTo(patientEntity.getAddress().getCity());
            assertThat(foundPatient.getAddress().getState()).isEqualTo(patientEntity.getAddress().getState());
            assertThat(foundPatient.getAddress().getPostalCode()).isEqualTo(patientEntity.getAddress().getPostalCode());
        });
    }

    @Test
    public void shouldFindPatientByCpf() {
        // Arrange
        PatientEntity patientEntity = PatientHelper.createPatientEntity(UUID.randomUUID());
        String cpf = patientEntity.getCpf();

        when(patientRepository.findByCpf(anyString())).thenReturn(Optional.of(patientEntity));

        // Act
        var optionalFoundPatient = patientGateway.findByCpf(cpf);

        // Assert
        verify(patientRepository, times(1)).findByCpf(anyString());

        assertThat(optionalFoundPatient).isNotEmpty();
        optionalFoundPatient.ifPresent(foundPatient -> {
            assertThat(foundPatient.getName()).isEqualTo(patientEntity.getName());
            assertThat(foundPatient.getGender()).isEqualTo(patientEntity.getGender());
            assertThat(foundPatient.getBirthDate()).isEqualTo(patientEntity.getBirthDate());
            assertThat(foundPatient.getCpf()).isEqualTo(patientEntity.getCpf());
            assertThat(foundPatient.getSusNumber()).isEqualTo(patientEntity.getSusNumber());
            assertThat(foundPatient.getPhoneNumber()).isEqualTo(patientEntity.getPhoneNumber());
            assertThat(foundPatient.getMail()).isEqualTo(patientEntity.getMail());
            assertThat(foundPatient.getAddress().getStreet()).isEqualTo(patientEntity.getAddress().getStreet());
            assertThat(foundPatient.getAddress().getNumber()).isEqualTo(patientEntity.getAddress().getNumber());
            assertThat(foundPatient.getAddress().getDistrict()).isEqualTo(patientEntity.getAddress().getDistrict());
            assertThat(foundPatient.getAddress().getCity()).isEqualTo(patientEntity.getAddress().getCity());
            assertThat(foundPatient.getAddress().getState()).isEqualTo(patientEntity.getAddress().getState());
            assertThat(foundPatient.getAddress().getPostalCode()).isEqualTo(patientEntity.getAddress().getPostalCode());
        });
    }

    @Test
    public void shouldFindAllPatient() {
        // Arrange
        PatientEntity patientEntity1 = PatientHelper.createPatientEntity(UUID.randomUUID());
        PatientEntity patientEntity2 = PatientHelper.createPatientEntity(UUID.randomUUID());

        when(patientRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(patientEntity1, patientEntity2)));

        // Act
        var foundPatients = patientGateway.findAll(PageRequest.of(0, 10));

        // Assert
        verify(patientRepository, times(1)).findAll(any(Pageable.class));

        assertThat(foundPatients).hasSize(2);
    }

}