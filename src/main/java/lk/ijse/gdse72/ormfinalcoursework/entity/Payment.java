package lk.ijse.gdse72.ormfinalcoursework.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id" , length=10)
    private String id;

    @Column(nullable = false, length = 20)
    private String sessionId;

    @Column(nullable = false)
    private String patientName;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 20)
    private String paymentMethod;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Column(length = 200)
    private String status;

    private BigDecimal paidAmount;

    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "program_id", nullable = false)
    private TherapyProgram therapyProgram;

    public Payment(String id, String sessionId, String patientName, BigDecimal amount, String paymentMethod, LocalDate paymentDate, String status, BigDecimal paidAmount, BigDecimal balance) {

        this.id = id;
        this.sessionId = sessionId;
        this.patientName = patientName;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.status = status;
        this.paidAmount = paidAmount;
        this.balance = balance;
    }
}