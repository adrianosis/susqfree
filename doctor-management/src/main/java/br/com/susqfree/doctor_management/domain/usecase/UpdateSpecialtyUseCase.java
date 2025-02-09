package br.com.susqfree.doctor_management.domain.usecase;

import br.com.susqfree.doctor_management.domain.gateway.SpecialtyGateway;
import br.com.susqfree.doctor_management.domain.model.Specialty;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateSpecialtyUseCase {

    private final SpecialtyGateway specialtyGateway;

    public UpdateSpecialtyUseCase(SpecialtyGateway specialtyGateway) {
        this.specialtyGateway = specialtyGateway;
    }

    public Specialty execute(Specialty specialty) {
        if (specialty.id() == null) {
            throw new IllegalArgumentException("Specialty ID must not be null for update.");
        }

        Optional<Specialty> existingSpecialty = specialtyGateway.findById(specialty.id());
        if (existingSpecialty.isEmpty()) {
            throw new IllegalStateException("Specialty with ID " + specialty.id() + " not found.");
        }

        return specialtyGateway.save(specialty);
    }
}
