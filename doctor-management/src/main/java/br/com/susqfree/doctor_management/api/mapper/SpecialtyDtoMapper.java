package br.com.susqfree.doctor_management.api.mapper;

import br.com.susqfree.doctor_management.api.dto.SpecialtyInput;
import br.com.susqfree.doctor_management.api.dto.SpecialtyOutput;
import br.com.susqfree.doctor_management.domain.model.Specialty;

public class SpecialtyDtoMapper {

    public static Specialty toDomain(SpecialtyInput input, Long id) {
        return new Specialty(
                id,
                input.name(),
                input.description()
        );
    }

    public static SpecialtyOutput toOutput(Specialty specialty) {
        return new SpecialtyOutput(
                specialty.id(),
                specialty.name(),
                specialty.description()
        );
    }
}
