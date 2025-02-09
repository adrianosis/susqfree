package br.com.susqfree.doctor_management.domain.usecase;

import br.com.susqfree.doctor_management.domain.gateway.SpecialtyGateway;

import org.springframework.stereotype.Service;

@Service
public class DeleteSpecialtyUseCase {

    private final SpecialtyGateway specialtyGateway;

    public DeleteSpecialtyUseCase(SpecialtyGateway specialtyGateway) {
        this.specialtyGateway = specialtyGateway;
    }

    public void execute(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Specialty ID must not be null.");
        }

        specialtyGateway.deleteById(id);
    }
}
