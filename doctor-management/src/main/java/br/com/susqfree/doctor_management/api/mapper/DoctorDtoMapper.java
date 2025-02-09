package br.com.susqfree.doctor_management.api.mapper;

import br.com.susqfree.doctor_management.api.dto.DoctorInput;
import br.com.susqfree.doctor_management.api.dto.DoctorOutput;
import br.com.susqfree.doctor_management.domain.model.Doctor;
import br.com.susqfree.doctor_management.domain.model.Specialty;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorDtoMapper {

    public static Doctor toDomain(DoctorInput input, Long id) {
        return new Doctor(
                id,
                input.name(),
                input.crm(),
                input.phone(),
                input.email(),
                input.specialtyIds().stream()
                        .map(specialtyId -> new Specialty(specialtyId, null, null))
                        .collect(Collectors.toList())
        );
    }

    public static DoctorOutput toOutput(Doctor doctor) {
        return new DoctorOutput(
                doctor.id(),
                doctor.name(),
                doctor.crm(),
                doctor.phone(),
                doctor.email(),
                doctor.specialties().stream()
                        .map(Specialty::id)
                        .toList()
        );
    }
}
