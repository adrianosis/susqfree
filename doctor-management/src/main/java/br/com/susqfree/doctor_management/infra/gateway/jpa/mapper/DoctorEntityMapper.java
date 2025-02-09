package br.com.susqfree.doctor_management.infra.gateway.jpa.mapper;

import br.com.susqfree.doctor_management.domain.model.Doctor;
import br.com.susqfree.doctor_management.domain.model.Specialty;
import br.com.susqfree.doctor_management.infra.gateway.jpa.entity.DoctorEntity;
import br.com.susqfree.doctor_management.infra.gateway.jpa.entity.SpecialtyEntity;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorEntityMapper {

    public static DoctorEntity toEntity(Doctor doctor) {
        List<SpecialtyEntity> specialties = doctor.specialties() != null
                ? doctor.specialties().stream()
                .map(specialty -> new SpecialtyEntity(specialty.id(), specialty.name(), specialty.description()))
                .collect(Collectors.toList())
                : List.of();

        return new DoctorEntity(
                doctor.id(),
                doctor.name(),
                doctor.crm(),
                doctor.phone(),
                doctor.email(),
                specialties
        );
    }

    public static Doctor toDomain(DoctorEntity entity) {
        List<Specialty> specialties = entity.getSpecialties() != null
                ? entity.getSpecialties().stream()
                .map(specialty -> new Specialty(specialty.getId(), specialty.getName(), specialty.getDescription()))
                .collect(Collectors.toList())
                : List.of();

        return new Doctor(
                entity.getId(),
                entity.getName(),
                entity.getCrm(),
                entity.getPhone(),
                entity.getEmail(),
                specialties
        );
    }
}
