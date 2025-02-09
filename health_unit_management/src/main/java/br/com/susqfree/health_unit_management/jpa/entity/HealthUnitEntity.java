package br.com.susqfree.health_unit_management.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "health_unit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthUnitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 20)
    private String type;

    @Column(nullable = false)
    private Integer capacity;

    @Column(length = 15)
    private String phone;

    @Column(nullable = false, length = 60)
    private String street;

    @Column(nullable = false, length = 10)
    private String number;

    @Column(length = 30)
    private String complement;

    @Column(nullable = false, length = 8)
    private String zipcode;

    @Column(nullable = false, length = 30)
    private String city;

    @Column(nullable = false, length = 2)
    private String state;

    @Column(precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(precision = 9, scale = 6)
    private BigDecimal longitude;
}
