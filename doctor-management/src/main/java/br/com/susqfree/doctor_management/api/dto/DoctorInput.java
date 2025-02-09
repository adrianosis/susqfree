package br.com.susqfree.doctor_management.api.dto;

import java.util.List;

public record DoctorInput(
        String name,
        String crm,
        String phone,
        String email,
        List<Long> specialtyIds
) {}
