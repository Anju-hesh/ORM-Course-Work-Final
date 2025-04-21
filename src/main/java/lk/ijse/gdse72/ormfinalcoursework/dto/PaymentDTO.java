package lk.ijse.gdse72.ormfinalcoursework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PaymentDTO {

    private String id;
    private String sessionId;
    private String patientName;
    private BigDecimal amount;
    private String paymentMethod;
    private LocalDate paymentDate;
    private String status;
    private BigDecimal paidAmount;
    private BigDecimal balance;

}
