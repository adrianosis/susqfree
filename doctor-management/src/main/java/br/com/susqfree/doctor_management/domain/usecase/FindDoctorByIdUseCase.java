package br.com.susqfree.doctor_management.domain.usecase;

import br.com.susqfree.doctor_management.domain.gateway.DoctorGateway;
import br.com.susqfree.doctor_management.domain.model.Doctor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindDoctorByIdUseCase {

    private final DoctorGateway doctorGateway;

    public FindDoctorByIdUseCase(DoctorGateway doctorGateway) {
        this.doctorGateway = doctorGateway;
    }

    public Optional<Doctor> execute(Long id) {
        return doctorGateway.findById(id);
    }
}
