package br.com.susqfree.emergency_care.infra.gateway.jpa.entity;

import br.com.susqfree.emergency_care.domain.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attendance")
public class AttendanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_unit_id", nullable = false)
    private ServiceUnitEntity serviceUnit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status;

    @Column(name = "ticket", length = 10, nullable = false)
    private String ticket;
}
