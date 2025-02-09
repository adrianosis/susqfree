package br.com.susqfree.doctor_management.domain.usecase;

import br.com.susqfree.doctor_management.domain.gateway.DoctorGateway;
import br.com.susqfree.doctor_management.domain.model.Doctor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateDoctorUseCase {

    private final DoctorGateway doctorGateway;

    public UpdateDoctorUseCase(DoctorGateway doctorGateway) {
        this.doctorGateway = doctorGateway;
    }

    public Doctor execute(Doctor doctor) {
        if (doctor.id() == null) {
            throw new IllegalArgumentException("Doctor ID must not be null for update.");
        }

        Optional<Doctor> existingDoctor = doctorGateway.findById(doctor.id());
        if (existingDoctor.isEmpty()) {
            throw new IllegalStateException("Doctor with ID " + doctor.id() + " not found.");
        }

        return doctorGateway.save(doctor);
    }
}
