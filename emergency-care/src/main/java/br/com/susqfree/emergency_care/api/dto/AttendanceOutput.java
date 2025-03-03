package br.com.susqfree.emergency_care.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class AttendanceOutput {
    private Long id;
    private UUID patientId;
    private String serviceType;
    private String status;
    private String ticket;
}
