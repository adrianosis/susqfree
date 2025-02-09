package br.com.susqfree.doctor_management.domain.model;

import java.util.List;

public record Doctor(
        Long id,
        String name,
        String crm,
        String phone,
        String email,
        List<Specialty> specialties
) {}
