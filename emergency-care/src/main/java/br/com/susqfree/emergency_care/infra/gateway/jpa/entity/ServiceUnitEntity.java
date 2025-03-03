package br.com.susqfree.emergency_care.infra.gateway.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "service_unit")
public class ServiceUnitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_type", nullable = false)
    private String serviceType;

    @Column(nullable = false)
    private Integer capacity;

    @Column(name = "unit_id", nullable = false)
    private Long unitId;
}
