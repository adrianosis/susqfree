package br.com.susqfree.doctor_management.domain.usecase;

import br.com.susqfree.doctor_management.domain.gateway.DoctorGateway;
import br.com.susqfree.doctor_management.domain.model.Doctor;

import org.springframework.stereotype.Service;

@Service
public class CreateDoctorUseCase {

    private final DoctorGateway doctorGateway;

    public CreateDoctorUseCase(DoctorGateway doctorGateway) {
        this.doctorGateway = doctorGateway;
    }

    public Doctor execute(Doctor doctor) {
        if (doctor.id() != null) {
            throw new IllegalArgumentException("New doctor must not have an ID.");
        }

        return doctorGateway.save(doctor);
    }
}
