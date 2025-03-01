package br.com.susqfree.schedule_management.utils;

import br.com.susqfree.schedule_management.domain.model.Doctor;
import br.com.susqfree.schedule_management.infra.gateway.db.mongo.document.DoctorDocument;
import br.com.susqfree.schedule_management.infra.gateway.integration.dto.DoctorDto;

public class DoctorHelper {

    public static Doctor createDoctor() {
        return Doctor.builder()
                .id(1L)
                .name("Doctor")
                .crm("1234")
                .build();
    }

    public static DoctorDocument createDoctorDocument() {
        return DoctorDocument.builder()
                .id(1L)
                .name("Doctor")
                .crm("1234")
                .build();
    }

    public static DoctorDto createDoctorDto() {
        return DoctorDto.builder()
                .id(1L)
                .name("Doctor")
                .crm("1234")
                .build();
    }

}
