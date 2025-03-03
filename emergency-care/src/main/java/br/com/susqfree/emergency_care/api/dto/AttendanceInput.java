package br.com.susqfree.emergency_care.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AttendanceInput {
    private UUID patientId;
    private Long serviceUnitId;
    private String priorityLevel;
}

