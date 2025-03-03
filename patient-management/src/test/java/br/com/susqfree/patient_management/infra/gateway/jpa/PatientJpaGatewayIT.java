package br.com.susqfree.patient_management.infra.gateway.jpa;

import br.com.susqfree.patient_management.domain.model.Address;
import br.com.susqfree.patient_management.domain.model.Patient;
import br.com.susqfree.patient_management.utils.PatientHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
public class PatientJpaGatewayIT {

    @Autowired
    private PatientJpaGateway patientGateway;

    private Patient patient;

    @BeforeEach
    public void setup() {
        patient = PatientHelper.createPatient(UUID.randomUUID());
        patientGateway.save(patient);
    }

    @Test
    public void shouldCreatePatient() {
        // Arrange
        UUID patientId = UUID.randomUUID();
        patient = PatientHelper.createPatient(patientId);

        // Act
        var savedPatient = patientGateway.save(patient);

        // Assert
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
        assertThat(savedPatient.getAddress().getLatitude()).isEqualTo(patient.getAddress().getLatitude());
        assertThat(savedPatient.getAddress().getLongitude()).isEqualTo(patient.getAddress().getLongitude());
    }

    @Test
    public void shouldFindPatientById() {
        // Arrange
        // Act
        var optionalFoundPatient = patientGateway.findById(patient.getId());

        // Assert
        assertThat(optionalFoundPatient).isNotEmpty();
        optionalFoundPatient.ifPresent(foundPatient -> {
            assertThat(foundPatient.getName()).isEqualTo(patient.getName());
            assertThat(foundPatient.getGender()).isEqualTo(patient.getGender());
            assertThat(foundPatient.getBirthDate()).isEqualTo(patient.getBirthDate());
            assertThat(foundPatient.getCpf()).isEqualTo(patient.getCpf());
            assertThat(foundPatient.getSusNumber()).isEqualTo(patient.getSusNumber());
            assertThat(foundPatient.getPhoneNumber()).isEqualTo(patient.getPhoneNumber());
            assertThat(foundPatient.getMail()).isEqualTo(patient.getMail());
            assertThat(foundPatient.getAddress().getStreet()).isEqualTo(patient.getAddress().getStreet());
            assertThat(foundPatient.getAddress().getNumber()).isEqualTo(patient.getAddress().getNumber());
            assertThat(foundPatient.getAddress().getDistrict()).isEqualTo(patient.getAddress().getDistrict());
            assertThat(foundPatient.getAddress().getCity()).isEqualTo(patient.getAddress().getCity());
            assertThat(foundPatient.getAddress().getState()).isEqualTo(patient.getAddress().getState());
            assertThat(foundPatient.getAddress().getPostalCode()).isEqualTo(patient.getAddress().getPostalCode());
            assertThat(foundPatient.getAddress().getLatitude()).isEqualTo(patient.getAddress().getLatitude());
            assertThat(foundPatient.getAddress().getLongitude()).isEqualTo(patient.getAddress().getLongitude());
        });
    }

    @Test
    public void shouldFindPatientByCpf() {
        // Arrange
        // Act
        var optionalFoundPatient = patientGateway.findByCpf(patient.getCpf());

        // Assert
        assertThat(optionalFoundPatient).isNotEmpty();
        optionalFoundPatient.ifPresent(foundPatient -> {
            assertThat(foundPatient.getName()).isEqualTo(patient.getName());
            assertThat(foundPatient.getGender()).isEqualTo(patient.getGender());
            assertThat(foundPatient.getBirthDate()).isEqualTo(patient.getBirthDate());
            assertThat(foundPatient.getCpf()).isEqualTo(patient.getCpf());
            assertThat(foundPatient.getSusNumber()).isEqualTo(patient.getSusNumber());
            assertThat(foundPatient.getPhoneNumber()).isEqualTo(patient.getPhoneNumber());
            assertThat(foundPatient.getMail()).isEqualTo(patient.getMail());
            assertThat(foundPatient.getAddress().getStreet()).isEqualTo(patient.getAddress().getStreet());
            assertThat(foundPatient.getAddress().getNumber()).isEqualTo(patient.getAddress().getNumber());
            assertThat(foundPatient.getAddress().getDistrict()).isEqualTo(patient.getAddress().getDistrict());
            assertThat(foundPatient.getAddress().getCity()).isEqualTo(patient.getAddress().getCity());
            assertThat(foundPatient.getAddress().getState()).isEqualTo(patient.getAddress().getState());
            assertThat(foundPatient.getAddress().getPostalCode()).isEqualTo(patient.getAddress().getPostalCode());
            assertThat(foundPatient.getAddress().getLatitude()).isEqualTo(patient.getAddress().getLatitude());
            assertThat(foundPatient.getAddress().getLongitude()).isEqualTo(patient.getAddress().getLongitude());
        });
    }

    @Test
    public void shouldFindAllPatient() {
        // Arrange
        Address address = Address.builder()
                .street("Av. Paulista")
                .number("1400")
                .district("Bela Vista")
                .city("São Paulo")
                .state("SP")
                .postalCode("12345678")
                .latitude(-23.562642)
                .longitude(-46.654887)
                .build();

        Patient patient1 =  Patient.builder()
                .id(UUID.randomUUID())
                .name("Julia")
                .gender("F")
                .birthDate(LocalDate.of(2005, 6, 24))
                .cpf("11133355511")
                .susNumber("123412341230")
                .phoneNumber("11999995555")
                .mail("julia@test.com")
                .address(address)
                .build();

        patientGateway.save(patient1);

        // Act
        var foundPatients = patientGateway.findAll(PageRequest.of(0, 10));

        // Assert
        assertThat(foundPatients).hasSize(2);
    }

}