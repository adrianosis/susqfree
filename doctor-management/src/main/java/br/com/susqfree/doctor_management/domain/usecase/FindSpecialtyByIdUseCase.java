package br.com.susqfree.doctor_management.domain.usecase;

import br.com.susqfree.doctor_management.domain.gateway.SpecialtyGateway;
import br.com.susqfree.doctor_management.domain.model.Specialty;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindSpecialtyByIdUseCase {

    private final SpecialtyGateway specialtyGateway;

    public FindSpecialtyByIdUseCase(SpecialtyGateway specialtyGateway) {
        this.specialtyGateway = specialtyGateway;
    }

    public Optional<Specialty> execute(Long id) {
        return specialtyGateway.findById(id);
    }

}
