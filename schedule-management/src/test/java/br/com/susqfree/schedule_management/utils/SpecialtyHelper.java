package br.com.susqfree.schedule_management.utils;

import br.com.susqfree.schedule_management.domain.model.Specialty;
import br.com.susqfree.schedule_management.infra.gateway.db.mongo.document.SpecialtyDocument;
import br.com.susqfree.schedule_management.infra.gateway.integration.dto.SpecialtyDto;

public class SpecialtyHelper {

    public static Specialty createSpecialty() {
        return Specialty.builder()
                .id(1L)
                .name("Cardiology")
                .build();
    }

    public static SpecialtyDocument createSpecialtyDocument() {
        return SpecialtyDocument.builder()
                .id(1L)
                .name("Cardiology")
                .build();
    }

    public static SpecialtyDto createSpecialtyDto() {
        return SpecialtyDto.builder()
                .id(1L)
                .name("Cardiology")
                .build();
    }

}
