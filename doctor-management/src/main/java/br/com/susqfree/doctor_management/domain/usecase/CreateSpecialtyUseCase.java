package br.com.susqfree.doctor_management.domain.usecase;

import br.com.susqfree.doctor_management.domain.gateway.SpecialtyGateway;
import br.com.susqfree.doctor_management.domain.model.Specialty;

import org.springframework.stereotype.Service;

@Service
public class CreateSpecialtyUseCase {

    private final SpecialtyGateway specialtyGateway;

    public CreateSpecialtyUseCase(SpecialtyGateway specialtyGateway) {
        this.specialtyGateway = specialtyGateway;
    }

    public Specialty execute(Specialty specialty) {
        if (specialty.id() != null) {
            throw new IllegalArgumentException("New specialty must not have an ID.");
        }

        return specialtyGateway.save(specialty);
    }
}
