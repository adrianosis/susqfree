package br.com.susqfree.doctor_management.domain.usecase;

import br.com.susqfree.doctor_management.domain.gateway.DoctorGateway;
import br.com.susqfree.doctor_management.domain.model.Doctor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindDoctorsBySpecialtyUseCase {

    private final DoctorGateway doctorGateway;

    public FindDoctorsBySpecialtyUseCase(DoctorGateway doctorGateway) {
        this.doctorGateway = doctorGateway;
    }

    public List<Doctor> execute(String specialtyName) {
        return doctorGateway.findBySpecialtyName(specialtyName);
    }
}
