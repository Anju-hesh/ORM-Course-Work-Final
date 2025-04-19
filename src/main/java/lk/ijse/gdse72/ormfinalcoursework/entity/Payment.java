package lk.ijse.gdse72.ormfinalcoursework.entity;

import jakarta.persistence.*;
import lk.ijse.gdse72.ormfinalcoursework.entity.Patient;
import lk.ijse.gdse72.ormfinalcoursework.entity.TherapyProgram;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false, length = 20, unique = true)
    private String invoiceNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "program_id", nullable = false)
    private TherapyProgram therapyProgram;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @Column(nullable = false, length = 20)
    private String paymentMethod; // Consider using enum

    @Column(length = 200)
    private String remarks;
}