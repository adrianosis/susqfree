package br.com.susqfree.doctor_management.domain.usecase;

import br.com.susqfree.doctor_management.domain.gateway.SpecialtyGateway;
import br.com.susqfree.doctor_management.domain.model.Specialty;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllSpecialtiesUseCase {

    private final SpecialtyGateway specialtyGateway;

    public FindAllSpecialtiesUseCase(SpecialtyGateway specialtyGateway) {
        this.specialtyGateway = specialtyGateway;
    }

    public List<Specialty> execute() {
        return specialtyGateway.findAll();
    }
}
