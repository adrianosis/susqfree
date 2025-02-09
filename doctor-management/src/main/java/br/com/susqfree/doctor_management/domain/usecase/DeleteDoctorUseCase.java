package br.com.susqfree.doctor_management.domain.usecase;

import br.com.susqfree.doctor_management.domain.gateway.DoctorGateway;

import org.springframework.stereotype.Service;

@Service
public class DeleteDoctorUseCase {

    private final DoctorGateway doctorGateway;

    public DeleteDoctorUseCase(DoctorGateway doctorGateway) {
        this.doctorGateway = doctorGateway;
    }

    public void execute(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Doctor ID must not be null.");
        }

        doctorGateway.deleteById(id);
    }
}
