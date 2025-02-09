package br.com.susqfree.doctor_management.infra.gateway.jpa.mapper;

import br.com.susqfree.doctor_management.domain.model.Specialty;
import br.com.susqfree.doctor_management.infra.gateway.jpa.entity.SpecialtyEntity;

public class SpecialtyEntityMapper {

    public static Specialty toDomain(SpecialtyEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Specialty(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }

    public static SpecialtyEntity toEntity(Specialty specialty) {
        if (specialty == null) {
            return null;
        }

        return new SpecialtyEntity(
                specialty.id(),
                specialty.name(),
                specialty.description()
        );
    }
}
